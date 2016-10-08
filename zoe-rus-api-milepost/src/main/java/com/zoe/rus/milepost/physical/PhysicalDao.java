package com.zoe.rus.milepost.physical;

import com.zoe.commons.dao.orm.PageList;

/**
 * @author lpw
 */
interface PhysicalDao {
    PageList<PhysicalModel> query(int pageSize, int pageNum);

    PageList<PhysicalModel> queryByRegion(String region);

    PageList<PhysicalModel> queryByHospital(String hospital);

    void save(PhysicalModel physical);

    void delete(String id);

    PhysicalModel findById(String id);
}
