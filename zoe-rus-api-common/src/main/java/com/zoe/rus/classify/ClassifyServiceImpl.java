package com.zoe.rus.classify;

import com.zoe.commons.util.Validator;
import net.sf.json.JSONArray;
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
    @Autowired
    protected Validator validator;
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

        return classifyDao.query(key, parent).getList();
    }

    @Override
    public void delete(String key) {
        classifyDao.delete(key);
    }

    @Override
    public void save(ClassifyModel classify) {
        classifyDao.save(classify);
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
}
