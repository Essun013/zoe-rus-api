package com.zoe.rus.classify;

import com.zoe.commons.dao.orm.PageList;

/**
 * @author lpw
 */
interface ClassifyDao {
    PageList<ClassifyModel> root(String key);

    PageList<ClassifyModel> children(String parent);

    ClassifyModel findById(String id);

    void save(ClassifyModel classify);

    void delete(String key);
}
