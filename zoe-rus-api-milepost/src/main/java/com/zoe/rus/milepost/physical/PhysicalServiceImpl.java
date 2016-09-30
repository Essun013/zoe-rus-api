package com.zoe.rus.milepost.physical;

import com.zoe.commons.bean.ContextRefreshedListener;
import com.zoe.commons.cache.Cache;
import com.zoe.rus.classify.ClassifyModel;
import com.zoe.rus.classify.ClassifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lpw
 */
@Service(PhysicalModel.NAME + ".service")
public class PhysicalServiceImpl implements PhysicalService, ContextRefreshedListener {
    @Autowired
    protected Cache cache;
    @Autowired
    protected ClassifyService classifyService;
    @Autowired
    protected PhysicalDao physicalDao;

    @Override
    public List<PhysicalModel> query(String region) {
        Map<String, PhysicalModel> map = new HashMap<>();
        query(map, region);

        return null;
    }

    protected void query(Map<String, PhysicalModel> map, String region) {
        if (region == null)
            return;

        ClassifyModel classify = classifyService.findById(region);
        if (classify == null)
            return;

        query(map, classify.getParent());
        physicalDao.query(region).getList().forEach(physical -> map.put(physical.getTime(), physical));
    }

    @Override
    public int getContextRefreshedSort() {
        return 99;
    }

    @Override
    public void onContextRefreshed() {
        System.out.println("##" + getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
        System.out.println("##" + System.getProperty("java.class.path"));
    }
}
