package com.zoe.rus.milepost.physical;

import com.zoe.commons.dao.orm.PageList;

import java.util.List;

/**
 * @author lpw
 */
public interface PhysicalService {
    String VALIDATOR_EXISTS = PhysicalModel.NAME + ".validator.exists";

    PageList<PhysicalModel> query();

    List<PhysicalModel> queryByRegion(String region);

    List<PhysicalModel> queryByHospital(String hospital);

    void save(PhysicalModel physical);

    void delete(String id);

    PhysicalModel findById(String id);
}
