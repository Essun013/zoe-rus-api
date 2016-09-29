package com.zoe.rus.kb.knowledge;

import com.zoe.commons.cache.Cache;
import com.zoe.commons.util.*;
import com.zoe.rus.classify.ClassifyModel;
import com.zoe.rus.classify.ClassifyService;
import com.zoe.rus.kb.keyword.KeyWordService;
import net.sf.json.JSONObject;
import org.commonmark.html.HtmlRenderer;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author lpw
 */
@Service(KnowledgeModel.NAME + ".service")
public class KnowledgeServiceImpl implements KnowledgeService {
    private static final String CLASSIFY_KEY = KnowledgeModel.NAME + ".classify";
    private static final String CACHE_HTML = KnowledgeModel.NAME + ".service.html:";

    @Autowired
    protected Cache cache;
    @Autowired
    protected Context context;
    @Autowired
    protected Converter converter;
    @Autowired
    protected Validator validator;
    @Autowired
    protected Json json;
    @Autowired
    protected Io io;
    @Autowired
    protected Logger logger;
    @Autowired
    protected ClassifyService classifyService;
    @Autowired
    protected KeyWordService keyWordService;
    @Autowired
    protected KnowledgeDao knowledgeDao;
    protected String md4solr;
    protected Map<String, String> path;
    protected Map<String, Set<String>> kws;

    @Override
    public String get(String id) {
        String key = CACHE_HTML + id;
        String html = cache.get(key);
        if (html == null) {
            KnowledgeModel knowledge = knowledgeDao.findById(id);
            if (knowledge == null)
                return null;

            cache.put(key, html = knowledge.getHtml(), false);
        }

        return html;
    }

    @Override
    public void reload() {
        clean();
        JSONObject json = new JSONObject();
        scan(null, json, null, new ClassifyModel(), new File(context.getAbsolutePath(PATH)));
        keyWordService.save(kws);
        try {
            Runtime.getRuntime().exec("sh " + context.getAbsolutePath("/WEB-INF/solr.sh")
                    + " /home/lpw/soft/solr-6.2.0 " + md4solr);
        } catch (IOException e) {
            logger.warn(e, "执行solr脚本时发生异常！");
        }
    }

    protected void clean() {
        classifyService.delete(CLASSIFY_KEY);
        knowledgeDao.delete();
        if (kws == null)
            kws = new HashMap<>();
        else
            kws.clear();
        if (path == null)
            path = new HashMap<>();
        else
            path.clear();

        md4solr = context.getAbsolutePath(PATH + "/md4solr") + "/";
        File md4solr = new File(this.md4solr);
        md4solr.mkdirs();
        File[] files = md4solr.listFiles();
        if (files != null)
            for (File file : files)
                file.delete();
    }

    protected void scan(JSONObject pjson, JSONObject json, ClassifyModel parent, ClassifyModel classify, File file) {
        String name = file.getName();
        if (!file.isDirectory() || (parent != null && !validator.isMatchRegex("^[0-9]{2}.+", name)))
            return;

        if (name.endsWith(".md"))
            knowledge(pjson, parent, file, name);
        else
            classify(json, parent, classify, file, name);
        if (pjson != null && !json.isEmpty())
            this.json.addAsArray(pjson, "children", json);
    }

    protected void classify(JSONObject json, ClassifyModel parent, ClassifyModel classify, File file, String name) {
        if (parent != null) {
            classify.setKey(CLASSIFY_KEY);
            classify.setSort(converter.toInt(name.substring(0, 2)));
            classify.setName(name.substring(2));
            classifyService.save(classify);
            if (parent.getChildren() == null)
                parent.setChildren(new ArrayList<>());
            parent.getChildren().add(classify);

            json.put("id", classify.getId());
            json.put("name", classify.getName());
        }
        File[] files = file.listFiles();
        if (files == null)
            return;

        Arrays.sort(files, (File o1, File o2) -> o1.getName().compareTo(o2.getName()));
        for (File f : files) {
            JSONObject object = new JSONObject();
            ClassifyModel child = new ClassifyModel();
            child.setParent(classify.getId());
            scan(json, object, classify, child, f);
        }
    }

    protected void knowledge(JSONObject json, ClassifyModel classify, File file, String name) {
        String md = getMdFile(file);
        if (md == null)
            return;

        KnowledgeModel knowledge = new KnowledgeModel();
        knowledge.setClassify(classify.getId());
        knowledge.setSort(converter.toInt(name.substring(0, 2)));
        knowledge.setSubject(name.substring(2, name.length() - 3));
        knowledge.setContent(new String(io.read(md)));
        Set<String> kws = new HashSet<>();
        List<String> mps = new ArrayList<>();
        knowledge.setHtml(toHtml(kws, mps, path(classify.getId()) + "/" + name + "/", knowledge.getContent()));
        knowledgeDao.save(knowledge);
        this.kws.put(knowledge.getId(), kws);
        io.copy(md, md4solr + knowledge.getId() + ".md");

        if (json == null)
            return;

        JSONObject object = new JSONObject();
        object.put("id", knowledge.getId());
        object.put("subject", knowledge.getSubject());
        this.json.addAsArray(json, "knowledge", object);
    }

    protected String getMdFile(File file) {
        File[] files = file.listFiles();
        if (files == null)
            return null;

        for (File f : files)
            if (f.getName().equals("kb.md"))
                return f.getAbsolutePath();

        return null;
    }

    protected String path(String id) {
        if (path.containsKey(id))
            return path.get(id);

        StringBuilder path = new StringBuilder();
        while (true) {
            if (id == null)
                return path.toString();

            ClassifyModel classify = classifyService.findById(id);
            if (classify == null) {
                this.path.put(id, path.toString());

                return this.path.get(id);
            }

            path.insert(0, "/" + converter.toString(classify.getSort(), "00") + classify.getName());
            id = classify.getParent();
        }
    }

    protected String toHtml(Set<String> kws, List<String> mps, String path, String md) {
        Parser parser = Parser.builder().build();
        Node node = parser.parse(md);
        node.accept(new KnowledgeVisitor(kws, mps, path));
        HtmlRenderer renderer = HtmlRenderer.builder().build();

        return renderer.render(node).replaceAll(KnowledgeVisitor.EMPTY_P, "").replaceAll(">\\s+",">");
    }
}
