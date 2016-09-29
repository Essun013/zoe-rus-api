package com.zoe.rus.classify;

import com.zoe.commons.dao.orm.PageList;

/**
 * @author lpw
 */
interface ClassifyDao {
    PageList<ClassifyModel> query(String key, String parent);

    ClassifyModel findById(String id);

    void save(ClassifyModel classify);

    void delete(String key);

    PageList<ClassifyModel> children(String parent);
}
