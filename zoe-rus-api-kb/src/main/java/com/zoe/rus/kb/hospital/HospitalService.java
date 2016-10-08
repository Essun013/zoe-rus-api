package com.zoe.rus.kb.hospital;

import java.util.List;

/**
 * @author lpw
 */
public interface HospitalService {
    List<HospitalModel> query(String region);

    void save(HospitalModel hospital);

    void delete(String id);

    HospitalModel findById(String id);
}
