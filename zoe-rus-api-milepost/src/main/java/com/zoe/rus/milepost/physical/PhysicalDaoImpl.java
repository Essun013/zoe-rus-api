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
    public PageList<PhysicalModel> query(int pageSize, int pageNum) {
        return liteOrm.query(new LiteQuery(PhysicalModel.class).order("c_region,c_hospital,c_type,c_sort").size(pageSize).page(pageNum), null);
    }

    @Override
    public PageList<PhysicalModel> queryByRegion(String region) {
        return liteOrm.query(new LiteQuery(PhysicalModel.class).where("c_region=?").order("c_type,c_sort"), new Object[]{region});
    }

    @Override
    public PageList<PhysicalModel> queryByHospital(String hospital) {
        return liteOrm.query(new LiteQuery(PhysicalModel.class).where("c_hospital=?").order("c_type,c_sort"), new Object[]{hospital});
    }

    @Override
    public void save(PhysicalModel physical) {
        liteOrm.save(physical);
    }

    @Override
    public void delete(String id) {
        liteOrm.delete(new LiteQuery(PhysicalModel.class).where("c_id=?"), new Object[]{id});
    }

    @Override
    public PhysicalModel findById(String id) {
        return liteOrm.findById(PhysicalModel.class, id);
    }
}
