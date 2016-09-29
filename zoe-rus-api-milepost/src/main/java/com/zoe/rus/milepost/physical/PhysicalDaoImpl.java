package com.zoe.rus.milepost.physical;

import com.zoe.commons.dao.orm.lite.LiteOrm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author lpw
 */
@Repository(PhysicalModel.NAME + ".dao")
class PhysicalDaoImpl implements PhysicalDao {
    @Autowired
    protected LiteOrm liteOrm;
}
