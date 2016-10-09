package com.zoe.rus.uc.auth;

import com.zoe.commons.dao.orm.lite.LiteOrm;
import com.zoe.commons.dao.orm.lite.LiteQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author lpw
 */
@Repository(AuthModel.NAME + ".dao")
class AuthDaoImpl implements AuthDao {
    @Autowired
    protected LiteOrm liteOrm;

    @Override
    public AuthModel findByUsername(String username) {
        return liteOrm.findOne(new LiteQuery(AuthModel.class).where("c_username=?"), new Object[]{username});
    }

    @Override
    public void save(AuthModel auth) {
        liteOrm.save(auth);
    }
}
