package com.zoe.rus.uc.user;

import com.zoe.commons.dao.orm.lite.LiteOrm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author lpw
 */
@Repository(UserModel.NAME + ".dao")
class UserDaoImpl implements UserDao {
    @Autowired
    protected LiteOrm liteOrm;

    @Override
    public UserModel findById(String id) {
        return liteOrm.findById(UserModel.class, id);
    }

    @Override
    public void save(UserModel user) {
        liteOrm.save(user);
    }

    @Override
    public void rollback() {
        liteOrm.rollback();
    }
}
