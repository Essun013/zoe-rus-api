package com.zoe.rus.uc.timeline;

import com.zoe.rus.uc.home.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author lpw
 */
@Service(TimelineModel.NAME + ".service")
public class TimelineServiceImpl implements TimelineService {
    private static final long LMP = 280L * 24 * 60 * 60 * 1000;

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
}
