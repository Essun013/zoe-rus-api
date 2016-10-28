package com.zoe.rus.kb.knowledge;

import com.zoe.commons.cache.Cache;
import com.zoe.commons.ctrl.context.Request;
import com.zoe.commons.dao.orm.PageList;
import com.zoe.commons.util.Context;
import com.zoe.commons.util.Converter;
import com.zoe.commons.util.Generator;
import com.zoe.commons.util.Io;
import com.zoe.commons.util.Json;
import com.zoe.commons.util.Logger;
import com.zoe.commons.util.Validator;
import com.zoe.rus.classify.ClassifyModel;
import com.zoe.rus.classify.ClassifyService;
import com.zoe.rus.kb.keyword.KeyWordService;
import com.zoe.rus.util.DateTime;
import com.zoe.rus.util.Pagination;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.commonmark.html.HtmlRenderer;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author lpw
 */
@Service(KnowledgeModel.NAME + ".service")
public class KnowledgeServiceImpl implements KnowledgeService {
    private static final String CLASSIFY_KEY = "kb.knowledge.classify";
    private static final String CACHE_JSON = KnowledgeModel.NAME + ".service.json:";
    private static final String CACHE_HTML = KnowledgeModel.NAME + ".service.html:";
    private static final String CACHE_LIST = KnowledgeModel.NAME + ".service.list:";
    private static final Pattern SORT_NAME = Pattern.compile("^\\d+");

    @Autowired
    protected Cache cache;
    @Autowired
    protected Context context;
    @Autowired
    protected Converter converter;
    @Autowired
    protected Validator validator;
    @Autowired
    protected Generator generator;
    @Autowired
    protected Json json;
    @Autowired
    protected Io io;
    @Autowired
    protected Logger logger;
    @Autowired
    protected Request request;
    @Autowired
    protected DateTime dateTime;
    @Autowired
    protected Pagination pagination;
    @Autowired
    protected ClassifyService classifyService;
    @Autowired
    protected KeyWordService keyWordService;
    @Autowired
    protected KnowledgeDao knowledgeDao;
    @Value("${" + KnowledgeModel.NAME + ".solr:}")
    protected String solr;
    protected String md4solr;
    protected String cacheRandom = "";
    protected Map<String, String> path;
    protected Map<String, Set<String>> kws;

    @Override
    public JSONObject get(String id) {
        String key = CACHE_JSON + id;
        JSONObject object = cache.get(key);
        if (object == null) {
            KnowledgeModel knowledge = knowledgeDao.findById(id);
            if (knowledge == null)
                return null;

            cache.put(key, object = toJson(knowledge), false);
        }

        return object;
    }

    @Override
    public JSONObject find(String subject) {
        String key = CACHE_JSON + cacheRandom + subject;
        JSONObject object = cache.get(key);
        if (object == null) {
            ClassifyModel classify = classifyService.find(CLASSIFY_KEY, 0);
            if (classify == null)
                return null;

            KnowledgeModel knowledge = knowledgeDao.findBySubject(classify.getId(), subject);
            if (knowledge == null)
                return null;

            cache.put(key, object = toJson(knowledge), false);
        }

        return object;
    }

    protected JSONObject toJson(KnowledgeModel knowledge) {
        JSONObject object = new JSONObject();
        object.put("id", knowledge.getId());
        object.put("subject", knowledge.getSubject());
        object.put("start", knowledge.getStart());
        object.put("end", knowledge.getEnd());
        if (!validator.isEmpty(knowledge.getImage()))
            object.put("image", knowledge.getImage());
        if (!validator.isEmpty(knowledge.getThumbnail()))
            object.put("thumbnail", knowledge.getThumbnail());
        object.put("summary", knowledge.getSummary());
        object.put("label", knowledge.getLabel());

        return object;
    }

    @Override
    public String getHtml(String id) {
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
    public JSONObject query(String[] classify, int day) {
        String key = CACHE_LIST + cacheRandom + converter.toString(classify) + day;
        JSONObject object = cache.get(key);
        if (object == null) {
            object = new JSONObject();
            Set<String> ids = getIds(classify);
            if (ids.isEmpty())
                putPageInfo(object, 0, 0, 0, new JSONArray());
            else {
                PageList<KnowledgeModel> pl = knowledgeDao.query(ids, day, pagination.getPageSize(), pagination.getPageNum());
                JSONArray array = new JSONArray();
                pl.getList().forEach(knowledge -> array.add(toJson(knowledge)));
                putPageInfo(object, pl.getCount(), pl.getSize(), pl.getNumber(), array);
            }
            cache.put(key, object, false);
        }

        return object;
    }

    protected Set<String> getIds(String[] classify) {
        Set<String> set = new HashSet<>();
        List<ClassifyModel> list = classifyService.find(CLASSIFY_KEY, classify);
        if (list.isEmpty())
            set.addAll(classifyService.links(CLASSIFY_KEY, classify[0]));
        else
            set.add(list.get(list.size() - 1).getId());

        Set<String> ids = new HashSet<>();
        if (set.isEmpty())
            return ids;

        set.forEach(id -> {
            ids.addAll(classifyService.children(id));
            ids.add(id);
        });

        return ids;
    }

    protected void putPageInfo(JSONObject object, int count, int size, int number, JSONArray list) {
        object.put("count", count);
        object.put("size", size);
        object.put("number", number);
        object.put("list", list);
    }

    @Override
    public synchronized void reload() {
        clean();
        JSONObject json = new JSONObject();
        scan(null, json, null, new ClassifyModel(), new File(context.getAbsolutePath(PATH)));
        keyWordService.save(kws);
        cacheRandom = generator.random(32);

        if (validator.isEmpty(solr))
            return;

        try {
            Runtime.getRuntime().exec("sh " + context.getAbsolutePath("/WEB-INF/solr.sh") + " " + solr + " " + md4solr);
        } catch (Exception e) {
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
        String[] sortName = getSortName(name);
        if (!file.isDirectory() || (parent != null && sortName == null))
            return;

        if (sortName == null)
            sortName = new String[]{"0", name};
        int sort = converter.toInt(sortName[0]);
        if (name.endsWith(".md"))
            knowledge(pjson, parent, file, sort, sortName[1], name);
        else
            classify(json, parent, classify, file, sort, sortName[1]);
        if (pjson != null && !json.isEmpty())
            this.json.addAsArray(pjson, "children", json);
    }

    protected String[] getSortName(String string) {
        Matcher matcher = SORT_NAME.matcher(string);
        if (!matcher.find())
            return null;

        String sort = matcher.group();

        return new String[]{sort, string.substring(sort.length())};
    }

    protected void classify(JSONObject json, ClassifyModel parent, ClassifyModel classify, File file, int sort, String name) {
        if (parent != null) {
            classify.setKey(CLASSIFY_KEY);
            classify.setSort(sort);
            classify.setName(name);
            classifyService.save(classify);

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

    protected void knowledge(JSONObject json, ClassifyModel classify, File file, int sort, String subject, String name) {
        String md = getMdFile(file);
        if (md == null)
            return;

        KnowledgeModel knowledge = new KnowledgeModel();
        knowledge.setClassify(classify.getId());
        knowledge.setSort(sort);
        knowledge.setSubject(subject.substring(0, subject.length() - 3));
        knowledge.setContent(new String(io.read(md)));
        Set<String> kws = new HashSet<>();
        StringBuilder mp = new StringBuilder();
        StringBuilder sm = new StringBuilder();
        StringBuilder lb = new StringBuilder();
        String path = KnowledgeService.PATH + path(classify.getId()) + "/" + name + "/";
        if (new File(context.getAbsolutePath(path + "image.png")).exists())
            knowledge.setImage(path + "image.png");
        if (new File(context.getAbsolutePath(path + "thumbnail.png")).exists())
            knowledge.setThumbnail(path + "thumbnail.png");
        knowledge.setHtml(toHtml(kws, mp, sm, lb, path, knowledge.getContent()));
        int[] range = dateTime.range(mp.toString());
        knowledge.setStart(range[0]);
        knowledge.setEnd(range[1]);
        knowledge.setSummary(sm.toString());
        knowledge.setLabel(lb.toString());
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

    protected String toHtml(Set<String> kws, StringBuilder mp, StringBuilder sm, StringBuilder lb, String path, String md) {
        Parser parser = Parser.builder().build();
        Node node = parser.parse(md);
        node.accept(new KnowledgeVisitor(kws, mp, sm, lb, path));
        HtmlRenderer renderer = HtmlRenderer.builder().build();

        return renderer.render(node).replaceAll(KnowledgeVisitor.EMPTY_P, "").replaceAll(">\\s+", ">");
    }
}
