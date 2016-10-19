package com.zoe.rus.milepost.physical;

import com.zoe.commons.cache.Cache;
import com.zoe.commons.dao.orm.PageList;
import com.zoe.commons.util.Validator;
import com.zoe.rus.classify.ClassifyModel;
import com.zoe.rus.classify.ClassifyService;
import com.zoe.rus.kb.hospital.HospitalModel;
import com.zoe.rus.kb.hospital.HospitalService;
import com.zoe.rus.util.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lpw
 */
@Service(PhysicalModel.NAME + ".service")
public class PhysicalServiceImpl implements PhysicalService {
    @Autowired
    protected Cache cache;
    @Autowired
    protected Validator validator;
    @Autowired
    protected Pagination pagination;
    @Autowired
    protected ClassifyService classifyService;
    @Autowired
    protected HospitalService hospitalService;
    @Autowired
    protected PhysicalDao physicalDao;
    protected String regionRoot;

    @Override
    public PageList<PhysicalModel> query() {
        return physicalDao.query(pagination.getPageSize(), pagination.getPageNum());
    }

    @Override
    public List<PhysicalModel> queryByRegion(String region) {
        return fromMap(queryMapByRegion(region));
    }

    @Override
    public List<PhysicalModel> queryByHospital(String hospitalId) {
        HospitalModel hospital = hospitalService.findById(hospitalId);
        if (hospital == null)
            return new ArrayList<>();

        Map<Integer, PhysicalModel> map = queryMapByRegion(hospital.getRegion());
        toMap(map, physicalDao.queryByHospital(hospitalId));

        return fromMap(map);
    }

    protected Map<Integer, PhysicalModel> queryMapByRegion(String region) {
        Map<Integer, PhysicalModel> map = new HashMap<>();
        queryByRegion(map, region);
        if (map.isEmpty())
            queryByRegion(getRegionRoot());

        return map;
    }

    protected void queryByRegion(Map<Integer, PhysicalModel> map, String region) {
        if (region == null)
            return;

        ClassifyModel classify = classifyService.findById(region);
        if (classify == null)
            return;

        queryByRegion(map, classify.getParent());
        toMap(map, physicalDao.queryByRegion(region));
    }

    protected void toMap(Map<Integer, PhysicalModel> map, PageList<PhysicalModel> pl) {
        pl.getList().forEach(physical -> map.put(physical.getType() * 10000 + physical.getSort(), physical));
    }

    protected List<PhysicalModel> fromMap(Map<Integer, PhysicalModel> map) {
        List<PhysicalModel> list = new ArrayList<>();
        if (validator.isEmpty(map))
            return list;

        List<Integer> keys = new ArrayList<>(map.keySet());
        Collections.sort(keys);
        keys.forEach(key -> list.add(map.get(key)));

        return list;
    }

    protected synchronized String getRegionRoot() {
        if (regionRoot == null) {
            List<ClassifyModel> list = classifyService.query("region", null);
            if (list.size() > 0)
                regionRoot = list.get(0).getId();
        }

        return regionRoot;
    }

    @Override
    public void save(PhysicalModel physical) {
        if (validator.isEmpty(physical.getId()))
            physical.setId(null);
        if (validator.isEmpty(physical.getRegion()))
            physical.setRegion("");
        if (validator.isEmpty(physical.getHospital()))
            physical.setHospital("");
        physicalDao.save(physical);
    }

    @Override
    public void delete(String id) {
        physicalDao.delete(id);
    }

    @Override
    public PhysicalModel findById(String id) {
        return physicalDao.findById(id);
    }
}
