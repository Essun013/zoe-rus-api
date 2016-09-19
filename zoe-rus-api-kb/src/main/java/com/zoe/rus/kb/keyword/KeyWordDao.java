package com.zoe.rus.kb.keyword;

import com.zoe.commons.dao.orm.PageList;

/**
 * @author lpw
 */
interface KeyWordDao {
    PageList<KeyWordModel> query();

    void delete();

    void save(KeyWordModel model);
}
