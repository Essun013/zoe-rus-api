package com.zoe.rus.milepost.physical;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lpw
 */
@Service(PhysicalModel.NAME + ".service")
public class PhysicalServiceImpl implements PhysicalService {
    @Autowired
    protected PhysicalDao physicalDao;
}
