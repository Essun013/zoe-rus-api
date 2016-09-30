package com.zoe.rus.milepost.physical;

import com.zoe.commons.dao.orm.PageList;

/**
 * @author lpw
 */
interface PhysicalDao {
    PageList<PhysicalModel> query(String region);
}
