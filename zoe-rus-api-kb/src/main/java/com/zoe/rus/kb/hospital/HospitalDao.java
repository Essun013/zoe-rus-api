package com.zoe.rus.kb.hospital;

import com.zoe.commons.dao.orm.PageList;

import java.util.Set;

/**
 * @author lpw
 */
interface HospitalDao {
    PageList<HospitalModel> query(Set<String> region);

    HospitalModel findById(String id);

    void save(HospitalModel hospital);

    void delete(HospitalModel hospital);
}
