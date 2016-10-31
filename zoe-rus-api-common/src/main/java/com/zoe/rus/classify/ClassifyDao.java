package com.zoe.rus.classify;

import com.zoe.commons.dao.orm.PageList;

import java.util.Set;

/**
 * @author lpw
 */
interface ClassifyDao {
    PageList<ClassifyModel> root(String key);

    PageList<ClassifyModel> children(String parent);

    ClassifyModel findById(String id);

    ClassifyModel findByKey(String key, String name);

    ClassifyModel findByParent(String parent, String name);

    void save(ClassifyModel classify);

    void delete(String key, Set<String> ignore);

    void delete(ClassifyModel classify);
}
