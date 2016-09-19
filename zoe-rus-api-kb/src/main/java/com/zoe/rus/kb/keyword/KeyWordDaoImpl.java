package com.zoe.rus.kb.keyword;

import com.zoe.commons.dao.orm.PageList;
import com.zoe.commons.dao.orm.lite.LiteOrm;
import com.zoe.commons.dao.orm.lite.LiteQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author lpw
 */
@Repository(KeyWordModel.NAME + ".dao")
class KeyWordDaoImpl implements KeyWordDao {
    @Autowired
    protected LiteOrm liteOrm;

    @Override
    public PageList<KeyWordModel> query() {
        return liteOrm.query(new LiteQuery(KeyWordModel.class), null);
    }

    @Override
    public void delete() {
        liteOrm.delete(new LiteQuery(KeyWordModel.class), null);
    }

    @Override
    public void save(KeyWordModel model) {
        liteOrm.save(model);
    }
}
