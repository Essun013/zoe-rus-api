package com.zoe.rus.kb.knowledge;

import com.zoe.commons.cache.Cache;
import com.zoe.commons.scheduler.MinuteJob;
import com.zoe.commons.util.Context;
import com.zoe.commons.util.Converter;
import com.zoe.commons.util.Io;
import com.zoe.rus.classify.ClassifyModel;
import com.zoe.rus.classify.ClassifyService;
import org.commonmark.html.HtmlRenderer;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * @author lpw
 */
@Service(KnowledgeModel.NAME + ".service")
public class KnowledgeServiceImpl implements KnowledgeService, MinuteJob {
    private static final String CLASSIFY_KEY = KnowledgeModel.NAME + ".classify";
    private static final String CACHE_HTML = KnowledgeModel.NAME + ".service.html:";

    @Autowired
    protected Cache cache;
    @Autowired
    protected Context context;
    @Autowired
    protected Converter converter;
    @Autowired
    protected Io io;
    @Autowired
    protected ClassifyService classifyService;
    @Autowired
    protected KnowledgeDao knowledgeDao;

    @Override
    public String get(String id) {
        String key = CACHE_HTML + id;
        String html = cache.get(key);
        if (html == null) {
            KnowledgeModel knowledge = knowledgeDao.findById(id);
            if (knowledge == null)
                return null;

            cache.put(key, html = toHtml(knowledge.getClassify(), knowledge.getContent()), false);
        }

        return html;
    }

    protected String path(String id) {
        StringBuilder path = new StringBuilder();
        while (true) {
            if (id == null)
                return path.toString();

            ClassifyModel classify = classifyService.findById(id);
            if (classify == null)
                return path.toString();

            path.insert(0, "/" + converter.toString(classify.getSort(), "00") + classify.getName());
            id = classify.getParent();
        }
    }

    protected String toHtml(String classify, String md) {
        Parser parser = Parser.builder().build();
        Node node = parser.parse(md);
        node.accept(new KnowledgeVisitor(path(classify)));
        HtmlRenderer renderer = HtmlRenderer.builder().build();

        return renderer.render(node);
    }

    @Override
    public void executeMinuteJob() {
        if (Calendar.getInstance().get(Calendar.MINUTE) % 5 > 0)
            return;

        classifyService.delete(CLASSIFY_KEY);
        knowledgeDao.delete();
        ClassifyModel classify = new ClassifyModel();
        scan(null, classify, new File(context.getAbsolutePath(PATH)));
    }

    protected void scan(ClassifyModel parent, ClassifyModel classify, File file) {
        directory(parent, classify, file);
        file(parent, file);
    }

    protected void directory(ClassifyModel parent, ClassifyModel classify, File file) {
        String name = file.getName();
        if (!file.isDirectory() || name.equals("img"))
            return;

        if (parent != null) {
            classify.setKey(CLASSIFY_KEY);
            classify.setSort(converter.toInt(name.substring(0, 2)));
            classify.setName(name.substring(2));
            classifyService.save(classify);
        }
        List<ClassifyModel> children = new ArrayList<>();
        for (File f : file.listFiles()) {
            ClassifyModel child = new ClassifyModel();
            child.setParent(classify.getId());
            scan(classify, child, f);
            children.add(child);
        }
        if (children.isEmpty())
            return;

        Collections.sort(children);
        classify.setChildren(children);
    }

    protected void file(ClassifyModel classify, File file) {
        String name = file.getName();
        int indexOf = name.lastIndexOf('.');
        if (!file.isFile() || indexOf == -1 || !name.substring(indexOf + 1).equals("md"))
            return;

        KnowledgeModel knowledge = new KnowledgeModel();
        knowledge.setClassify(classify.getId());
        knowledge.setSort(converter.toInt(name.substring(0, 2)));
        knowledge.setSubject(name.substring(2, indexOf));
        knowledge.setContent(new String(io.read(file.getAbsolutePath())));
        knowledgeDao.save(knowledge);
    }
}
