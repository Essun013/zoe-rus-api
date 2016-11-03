package com.zoe.rus.classify;

import com.zoe.commons.cache.Cache;
import com.zoe.commons.dao.model.ModelHelper;
import com.zoe.commons.dao.orm.PageList;
import com.zoe.commons.util.Converter;
import com.zoe.commons.util.Validator;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author lpw
 */
@Service(ClassifyModel.NAME + ".service")
public class ClassifyServiceImpl implements ClassifyService {
    private static final String CACHE_NAMES = ClassifyModel.NAME + ".service.names:";
    private static final String CACHE_TREE = ClassifyModel.NAME + ".service.tree:";
    private static final String LINK_KEY = "link";

    @Autowired
    protected Validator validator;
    @Autowired
    protected Converter converter;
    @Autowired
    protected Cache cache;
    @Autowired
    protected ModelHelper modelHelper;
    @Autowired
    protected ClassifyDao classifyDao;

    @Override
    public void save(String id, String key, String parent, int sort, String name) {
        if (validator.isEmpty(id) && validator.isEmpty(key) && validator.isEmpty(parent))
            return;

        ClassifyModel classify = null;
        if (!validator.isEmpty(id))
            classify = classifyDao.findById(id);
        if (classify == null)
            classify = new ClassifyModel();
        if (!validator.isEmpty(key))
            classify.setKey(key);
        if (!validator.isEmpty(parent))
            classify.setParent(parent);
        classify.setSort(sort);
        classify.setName(name);
        classifyDao.save(classify);
    }

    @Override
    public List<ClassifyModel> query(String key, String parent) {
        if (validator.isEmpty(key) && validator.isEmpty(parent))
            return new ArrayList<>();

        return (validator.isEmpty(parent) ? classifyDao.root(key) : classifyDao.children(parent)).getList();
    }

    @Override
    public void delete(String key, Set<String> ignore) {
        classifyDao.delete(key, ignore);
        cache.remove(CACHE_TREE + key);
    }

    @Override
    public void save(ClassifyModel classify) {
        if (classify.getId() == null) {
            ClassifyModel model = validator.isEmpty(classify.getParent()) ?
                    classifyDao.findByKey(classify.getKey(), classify.getName()) : classifyDao.findByParent(classify.getParent(), classify.getName());
            if (model != null)
                classify.setId(model.getId());
        }
        classifyDao.save(classify);
        cache.remove(CACHE_TREE + classify.getKey());
    }

    @Override
    public ClassifyModel findById(String id) {
        return classifyDao.findById(id);
    }

    @Override
    public Set<String> parent(String id) {
        Set<String> set = new HashSet<>();
        parent(set, id);

        return set;
    }

    protected void parent(Set<String> set, String id) {
        ClassifyModel classify = classifyDao.findById(id);
        if (classify == null || classify.getParent() == null)
            return;

        set.add(classify.getParent());
        parent(set, classify.getParent());
    }

    @Override
    public Set<String> children(String id) {
        Set<String> set = new HashSet<>();
        children(set, id);

        return set;
    }

    protected void children(Set<String> set, String parent) {
        classifyDao.children(parent).getList().forEach(child -> {
            set.add(child.getId());
            children(child.getId());
        });
    }

    @Override
    public ClassifyModel find(String key, int sort) {
        for (ClassifyModel classify : classifyDao.root(key).getList())
            if (classify.getSort() == sort)
                return classify;

        return null;
    }

    @Override
    public List<ClassifyModel> find(String key, String[] names) {
        String cacheKey = CACHE_NAMES + converter.toString(names);
        List<ClassifyModel> list = cache.get(cacheKey);
        if (list == null) {
            list = new ArrayList<>();
            find(list, classifyDao.root(key), names, 0);
            cache.put(cacheKey, list, false);
        }

        return list;
    }

    protected void find(List<ClassifyModel> list, PageList<ClassifyModel> pl, String[] names, int index) {
        if (pl.getList().isEmpty())
            return;

        String name = names[index];
        for (ClassifyModel classify : pl.getList()) {
            if (classify.getName().contains(name)) {
                list.add(classify);
                if (index < names.length - 1)
                    find(list, classifyDao.children(classify.getId()), names, index + 1);

                break;
            }
        }
    }

    @Override
    public void link(String name, String label) {
        ClassifyModel classify = new ClassifyModel();
        classify.setKey(LINK_KEY);
        classify.setName(name);
        classify.setLabel(label);
        classifyDao.root(LINK_KEY).getList().forEach(model -> classifyDao.delete(model));
        classifyDao.save(classify);
    }

    @Override
    public Set<String> links(String key, String name) {
        Set<String> set = new HashSet<>();
        classifyDao.root(LINK_KEY).getList().forEach(model -> {
            for (String[] names : converter.toArray(model.getLabel(), new String[]{";", ""})) {
                List<ClassifyModel> list = find(key, names);
                if (!list.isEmpty())
                    set.add(list.get(list.size() - 1).getId());
            }
        });

        return set;
    }

    @Override
    public JSONArray tree(String key) {
        String cacheKey = CACHE_TREE + key;
        JSONArray array = cache.get(cacheKey);
        if (array == null) {
            array = new JSONArray();
            tree(array, classifyDao.root(key).getList());
            cache.put(cacheKey, array, false);
        }

        return array;
    }

    protected void tree(JSONArray array, List<ClassifyModel> list) {
        if (list.isEmpty())
            return;

        list.forEach(classify -> {
            JSONObject object = modelHelper.toJson(classify);
            List<ClassifyModel> children = classifyDao.children(classify.getId()).getList();
            if (!children.isEmpty()) {
                JSONArray arr = new JSONArray();
                tree(arr, children);
                object.put("children", arr);
            }
            array.add(object);
        });
    }
}
