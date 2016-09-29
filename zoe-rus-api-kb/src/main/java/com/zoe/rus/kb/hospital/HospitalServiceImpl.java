package com.zoe.rus.kb.hospital;

import com.zoe.commons.cache.Cache;
import com.zoe.rus.classify.ClassifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @author lpw
 */
@Service(HospitalModel.NAME + ".service")
public class HospitalServiceImpl implements HospitalService {
    private static final String CACHE_REGION = HospitalModel.NAME + ".service.region:";

    @Autowired
    protected Cache cache;
    @Autowired
    protected ClassifyService classifyService;
    @Autowired
    protected HospitalDao hospitalDao;

    @Override
    public List<HospitalModel> query(String region) {
        String key = CACHE_REGION + region;
        List<HospitalModel> list = cache.get(key);
        if (list == null) {
            Set<String> set = classifyService.children(region);
            set.add(region);
            cache.put(key, list = hospitalDao.query(set).getList(), false);
        }

        return list;
    }

    @Override
    public void save(HospitalModel hospital) {
        if (hospital.getId() != null && hospitalDao.findById(hospital.getId()) == null)
            hospital.setId(null);
        hospitalDao.save(hospital);
        clearCache(hospital);
    }

    @Override
    public void delete(String id) {
        HospitalModel hospital = hospitalDao.findById(id);
        if (hospital == null)
            return;

        hospitalDao.delete(hospital);
        clearCache(hospital);
    }

    protected void clearCache(HospitalModel hospital) {
        cache.remove(CACHE_REGION + hospital.getRegion());
        classifyService.parent(hospital.getRegion()).forEach(region -> cache.remove(CACHE_REGION + region));
    }
}
