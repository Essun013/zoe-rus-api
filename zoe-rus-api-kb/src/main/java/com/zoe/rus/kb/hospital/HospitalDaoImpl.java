package com.zoe.rus.kb.hospital;

import com.zoe.commons.dao.orm.PageList;
import com.zoe.commons.dao.orm.lite.LiteOrm;
import com.zoe.commons.dao.orm.lite.LiteQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * @author lpw
 */
@Repository(HospitalModel.NAME + ".dao")
class HospitalDaoImpl implements HospitalDao {
    @Autowired
    protected LiteOrm liteOrm;

    @Override
    public PageList<HospitalModel> query(Set<String> region) {
        StringBuilder where = new StringBuilder();
        for (int i = 0, size = region.size(); i < size; i++)
            where.append(",?");

        return liteOrm.query(new LiteQuery(HospitalModel.class).where(where.replace(0, 1, "c_region in(").append(')').toString()), region.toArray());
    }

    @Override
    public HospitalModel findById(String id) {
        return liteOrm.findById(HospitalModel.class, id);
    }

    @Override
    public void save(HospitalModel hospital) {
        liteOrm.save(hospital);
    }

    @Override
    public void delete(HospitalModel hospital) {
        liteOrm.delete(hospital);
    }
}
