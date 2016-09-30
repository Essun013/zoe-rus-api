package com.zoe.rus.milepost.physical;

import java.util.List;

/**
 * @author lpw
 */
public interface PhysicalService {
    List<PhysicalModel> query(String region);
}
