package com.zoe.rus.uc.timeline;

import com.zoe.commons.ctrl.context.Session;
import com.zoe.rus.milepost.physical.PhysicalService;
import com.zoe.rus.uc.home.HomeService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author lpw
 */
@Service(TimelineModel.NAME + ".service")
public class TimelineServiceImpl implements TimelineService {
    private static final long DAY = 24L * 60 * 60 * 1000;
    private static final long LMP = 280L * DAY;
    private static final String SESSION = TimelineModel.NAME + ".service.session";

    @Autowired
    protected Session session;
    @Autowired
    protected PhysicalService physicalService;
    @Autowired
    protected HomeService homeService;
    @Autowired
    protected TimelineDao timelineDao;

    @Override
    public boolean create(Date lmp, Date childbirth, Date birthday) {
        if (lmp == null && childbirth == null && birthday == null)
            return false;

        TimelineModel timeline = new TimelineModel();
        timeline.setHome(homeService.get().getId());
        if (birthday == null) {
            if (childbirth != null)
                lmp = new Date(childbirth.getTime() - LMP);
            timeline.setStart(new java.sql.Date(lmp.getTime()));
        } else {
            timeline.setType(1);
            timeline.setStart(new java.sql.Date(birthday.getTime()));
        }
        timelineDao.save(timeline);
        sort(timeline.getHome());
        physical(timeline);
        set(timeline, true);

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
        JSONArray array = new JSONArray();
        physicalService.queryByRegion("").forEach(physical -> array.add(physical.getContent()));
        json.put("physical", array);
        timelineDao.insertPhysical(json);
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
        TimelineModel timeline = session.get(SESSION);
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
        session.set(SESSION, timeline);

        return timeline;
    }
}
