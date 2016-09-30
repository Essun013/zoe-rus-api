package com.zoe.rus.milepost.physical;

import com.zoe.commons.dao.orm.PageList;
import com.zoe.commons.dao.orm.lite.LiteOrm;
import com.zoe.commons.dao.orm.lite.LiteQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author lpw
 */
@Repository(PhysicalModel.NAME + ".dao")
class PhysicalDaoImpl implements PhysicalDao {
    @Autowired
    protected LiteOrm liteOrm;

    @Override
    public PageList<PhysicalModel> query(String region) {
        return liteOrm.query(new LiteQuery(PhysicalModel.class).where("c_region=?"), new Object[]{region});
    }
}
