package com.zoe.rus.kb.knowledge;

import com.zoe.commons.cache.Cache;
import com.zoe.commons.ctrl.context.Request;
import com.zoe.commons.dao.orm.PageList;
import com.zoe.commons.scheduler.HourJob;
import com.zoe.commons.util.Context;
import com.zoe.commons.util.Converter;
import com.zoe.commons.util.Generator;
import com.zoe.commons.util.Io;
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
public class KnowledgeServiceImpl implements KnowledgeService, HourJob {
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
    protected Set<String> classifyIds;
    protected Set<String> knowledgeIds;

    @Override
    public JSONObject get(String id) {
        String key = CACHE_JSON + cacheRandom + id;
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
        object.put("read", knowledge.getRead());
        object.put("favorite", knowledge.getFavorite());

        return object;
    }

    @Override
    public String getHtml(String id) {
        String key = CACHE_HTML + cacheRandom + id;
        String html = cache.get(key);
        if (html == null) {
            KnowledgeModel knowledge = knowledgeDao.findById(id);
            if (knowledge == null)
                return null;

            cache.put(key, html = knowledge.getHtml(), false);
        }
        knowledgeDao.read(id);

        return html;
    }

    @Override
    public JSONObject query(String[] classify, int day, boolean image) {
        int pageSize = pagination.getPageSize();
        int pageNum = pagination.getPageNum();
        String key = CACHE_LIST + cacheRandom + converter.toString(classify) + day + image + pageSize + pageNum;
        JSONObject object = cache.get(key);
        if (object == null) {
            Set<String> ids = getIds(classify);
            PageList<KnowledgeModel> pl = knowledgeDao.query(ids, day, image, pageSize, pageNum);
            cache.put(key, object = toJson(pl), false);
        }

        return object;
    }

    protected Set<String> getIds(String[] classify) {
        Set<String> set = new HashSet<>();
        if (validator.isEmpty(classify))
            return set;

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

    protected JSONObject toJson(PageList<KnowledgeModel> pl) {
        JSONObject object = new JSONObject();
        JSONArray array = new JSONArray();
        pl.getList().forEach(knowledge -> array.add(toJson(knowledge)));
        object.put("count", pl.getCount());
        object.put("size", pl.getSize());
        object.put("number", pl.getNumber());
        object.put("list", array);

        return object;
    }

    @Override
    public void favorite(String id, Favorite favorite) {
        knowledgeDao.favorite(id, favorite == Favorite.Remove ? -1 : 1);
    }

    @Override
    public JSONObject search(String keyword) {
        return toJson(knowledgeDao.query(keyword, pagination.getPageSize(), pagination.getPageNum()));
    }

    @Override
    public synchronized void reload() {
        clean();
        scan(null, new ClassifyModel(), new File(context.getAbsolutePath(PATH)));
        keyWordService.save(kws);
        classifyService.delete(CLASSIFY_KEY, classifyIds);
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
        if (kws == null)
            kws = new HashMap<>();
        else
            kws.clear();
        if (path == null)
            path = new HashMap<>();
        else
            path.clear();
        if (classifyIds == null)
            classifyIds = new HashSet<>();
        else
            classifyIds.clear();
        if (knowledgeIds == null)
            knowledgeIds = new HashSet<>();
        else
            knowledgeIds.clear();

        md4solr = context.getAbsolutePath(PATH + "/md4solr") + "/";
        File md4solr = new File(this.md4solr);
        md4solr.mkdirs();
        File[] files = md4solr.listFiles();
        if (files != null)
            for (File file : files)
                file.delete();
    }

    protected void scan(ClassifyModel parent, ClassifyModel classify, File file) {
        String name = file.getName();
        String[] sortName = getSortName(name);
        if (!file.isDirectory() || (parent != null && sortName == null))
            return;

        if (sortName == null)
            sortName = new String[]{"0", name};
        int sort = converter.toInt(sortName[0]);
        if (name.endsWith(".md"))
            knowledge(parent, file, sort, sortName[1], name);
        else
            classify(parent, classify, file, sort, sortName[1]);
    }

    protected String[] getSortName(String string) {
        Matcher matcher = SORT_NAME.matcher(string);
        if (!matcher.find())
            return null;

        String sort = matcher.group();

        return new String[]{sort, string.substring(sort.length())};
    }

    protected void classify(ClassifyModel parent, ClassifyModel classify, File file, int sort, String name) {
        if (parent != null) {
            classify.setKey(CLASSIFY_KEY);
            classify.setSort(sort);
            classify.setName(name);
            classifyService.save(classify);
            classifyIds.add(classify.getId());
        }
        File[] files = file.listFiles();
        if (files == null)
            return;

        Arrays.sort(files, (File o1, File o2) -> o1.getName().compareTo(o2.getName()));
        for (File f : files) {
            ClassifyModel child = new ClassifyModel();
            child.setParent(classify.getId());
            scan(classify, child, f);
        }
    }

    protected void knowledge(ClassifyModel classify, File file, int sort, String subject, String name) {
        String md = getMdFile(file);
        if (md == null)
            return;

        String sbj = subject.substring(0, subject.length() - 3);
        KnowledgeModel knowledge = knowledgeDao.findBySubject(classify.getId(), sbj);
        if (knowledge == null) {
            knowledge = new KnowledgeModel();
            knowledge.setClassify(classify.getId());
        }
        knowledge.setSort(sort);
        knowledge.setSubject(sbj);
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
        knowledgeIds.add(knowledge.getId());
        this.kws.put(knowledge.getId(), kws);
        io.copy(md, md4solr + knowledge.getId() + ".md");
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

        return renderer.render(node).replaceAll(KnowledgeVisitor.EMPTY_PP, "").replaceAll(KnowledgeVisitor.EMPTY_P, "<p>");
    }

    @Override
    public void executeHourJob() {
        cacheRandom = generator.random(32);
    }
}
