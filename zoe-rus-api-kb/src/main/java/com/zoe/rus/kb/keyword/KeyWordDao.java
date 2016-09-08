package com.zoe.rus.kb.keyword;

import com.zoe.commons.dao.orm.PageList;

/**
 * @author lpw
 */
interface KeyWordDao {
    PageList<KeyWordModel> query();

    void delete(String knowledge);

    void save(KeyWordModel model);
}
