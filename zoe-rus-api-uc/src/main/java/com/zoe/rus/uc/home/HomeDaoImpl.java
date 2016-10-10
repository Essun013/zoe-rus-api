package com.zoe.rus.uc.home;

import com.zoe.commons.dao.orm.lite.LiteOrm;
import com.zoe.commons.dao.orm.lite.LiteQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author lpw
 */
@Repository(HomeModel.NAME + ".dao")
class HomeDaoImpl implements HomeDao {
    @Autowired
    protected LiteOrm liteOrm;

    @Override
    public HomeModel findById(String id) {
        return liteOrm.findById(HomeModel.class, id);
    }

    @Override
    public HomeModel findByCode(String code) {
        return liteOrm.findOne(new LiteQuery(HomeModel.class).where("c_code=?"), new Object[]{code});
    }

    @Override
    public void save(HomeModel home) {
        liteOrm.save(home);
    }
}
