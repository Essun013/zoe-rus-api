package com.zoe.rus.uc.timeline;

import com.zoe.commons.ctrl.context.Session;
import com.zoe.commons.dao.model.ModelHelper;
import com.zoe.commons.util.Converter;
import com.zoe.commons.util.Validator;
import com.zoe.rus.milepost.physical.PhysicalModel;
import com.zoe.rus.milepost.physical.PhysicalService;
import com.zoe.rus.uc.home.HomeService;
import com.zoe.rus.util.DateTime;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lpw
 */
@Service(TimelineModel.NAME + ".service")
public class TimelineServiceImpl implements TimelineService {
    private static final long DAY = 24L * 60 * 60 * 1000;
    private static final long LMP = 280L * DAY;
    private static final String SESSION_TIMELINE = TimelineModel.NAME + ".service.session.timeline";
    private static final String SESSION_PHYSICAL = TimelineModel.NAME + ".service.session.physical";

    @Autowired
    protected Validator validator;
    @Autowired
    protected Converter converter;
    @Autowired
    protected ModelHelper modelHelper;
    @Autowired
    protected Session session;
    @Autowired
    protected DateTime dateTime;
    @Autowired
    protected PhysicalService physicalService;
    @Autowired
    protected HomeService homeService;
    @Autowired
    protected TimelineDao timelineDao;

    @Override
    public boolean create(Date lmp, Date childbirth, Date birthday, String region, String hospital) {
        TimelineModel timeline = new TimelineModel();
        if (!resetStart(timeline, lmp, childbirth, birthday))
            return false;

        timeline.setHome(homeService.get().getId());
        if (!validator.isEmpty(region))
            timeline.setRegion(region);
        if (!validator.isEmpty(hospital))
            timeline.setHospital(hospital);
        timelineDao.save(timeline);
        sort(timeline.getHome());
        physical(timeline);
        set(timeline, true);

        return true;
    }

    protected boolean resetStart(TimelineModel timeline, Date lmp, Date childbirth, Date birthday) {
        if (lmp == null && childbirth == null && birthday == null)
            return false;

        if (birthday == null) {
            if (childbirth != null)
                lmp = new Date(childbirth.getTime() - LMP);
            timeline.setStart(new java.sql.Date(lmp.getTime()));
        } else {
            timeline.setType(1);
            timeline.setStart(new java.sql.Date(birthday.getTime()));
        }

        return true;
    }

    protected void sort(String home) {
        List<TimelineModel> list = timelineDao.query(home, false).getList();
        for (int i = 0; i < list.size(); i++) {
            TimelineModel timeline = list.get(i);
            timeline.setSort(i + 1);
            timelineDao.save(timeline);
        }
    }

    protected void physical(TimelineModel timeline) {
        JSONObject json = new JSONObject();
        json.put("id", timeline.getId());
        json.put("physical", physical(physicalService.query(timeline.getRegion(), timeline.getHospital())));
        timelineDao.insertPhysical(json);
    }

    protected JSONArray physical(List<PhysicalModel> physicals) {
        JSONArray array = new JSONArray();
        physicals.forEach(physical -> {
            JSONObject object = modelHelper.toJson(physical);
            object.put("dayRange", dateTime.range(physical.getTime()));
            array.add(object);
        });

        return array;
    }

    @Override
    public void modify(Date lmp, Date childbirth, Date birthday, String region, String hospital) {
        TimelineModel timeline = get();
        resetStart(timeline, lmp, childbirth, birthday);
        List<PhysicalModel> physicals = physicalService.query(region, hospital);
        if (!physicals.isEmpty()) {
            Map<Integer, JSONObject> map = new HashMap<>();
            JSONObject physical = timelineDao.getPhysical(timeline.getId());
            mergeToMap(map, physical.getJSONArray("physical"), false);
            mergeToMap(map, physical(physicals), true);
            physical.put("physical", toArray(map));
        }
        timeline.setRegion(region);
        timeline.setHospital(hospital);
        timelineDao.save(timeline);
        set(timeline, true);
    }

    protected void mergeToMap(Map<Integer, JSONObject> map, JSONArray array, boolean checkable) {
        for (int i = 0, size = array.size(); i < size; i++) {
            JSONObject object1 = array.getJSONObject(i);
            int sort = object1.getInt("sort");
            if (checkable) {
                JSONObject object2 = map.get(sort);
                if (object2.has("done"))
                    continue;
            }
            map.put(sort, object1);
        }
    }

    protected JSONArray toArray(Map<Integer, JSONObject> map) {
        JSONArray array = new JSONArray();
        List<Integer> list = new ArrayList<>(map.keySet());
        Collections.sort(list);
        list.forEach(key -> array.add(map.get(key)));

        return array;
    }

    @Override
    public void portrait(String portrait) {
        TimelineModel timeline = get();
        if (timeline == null)
            return;

        timeline.setPortrait(portrait);
        timelineDao.save(timeline);
        set(timeline, false);
    }

    @Override
    public TimelineModel get() {
        TimelineModel timeline = session.get(SESSION_TIMELINE);
        if (timeline == null) {
            List<TimelineModel> list = timelineDao.query(homeService.get().getId(), true).getList();
            if (list.isEmpty())
                return null;

            timeline = set(list.get(list.size() - 1), true);
        }

        return timeline;
    }

    protected TimelineModel set(TimelineModel timeline, boolean reset) {
        if (reset) {
            timeline.setDay((int) ((System.currentTimeMillis() - timeline.getStart().getTime()) / DAY));
            timelineDao.save(timeline);
        }
        session.set(SESSION_TIMELINE, timeline);
        setPhysical(timeline);

        return timeline;
    }

    @Override
    public JSONObject getPhysical() {
        JSONObject physical = session.get(SESSION_PHYSICAL);
        if (physical == null)
            physical = setPhysical(get());

        return physical;
    }

    protected JSONObject setPhysical(TimelineModel timeline) {
        JSONArray array = timelineDao.getPhysical(timeline.getId()).getJSONArray("physical");
        for (int i = 0, size = array.size(); i < size; i++) {
            JSONObject object = array.getJSONObject(i);
            JSONArray dayRange = object.getJSONArray("dayRange");
            int[] ns = new int[]{dayRange.getInt(0), dayRange.getInt(1)};
            if (i == size - 1 || (timeline.getDay() >= ns[0] && timeline.getDay() <= ns[1])) {
                session.set(SESSION_PHYSICAL, object);

                return object;
            }
        }

        return new JSONObject();
    }

    @Override
    public void done(String id) {
        JSONObject physical = timelineDao.getPhysical(get().getId());
        JSONArray array = physical.getJSONArray("physical");
        for (int i = 0, size = array.size(); i < size; i++) {
            JSONObject object = array.getJSONObject(i);
            if (object.get("id").equals(id)) {
                object.put("done", converter.toString(new Date()));

                break;
            }
        }
        timelineDao.updatePhysical(get().getId(), physical);
        set(get(), true);
    }
}
