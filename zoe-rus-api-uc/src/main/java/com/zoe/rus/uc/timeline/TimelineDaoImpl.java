package com.zoe.rus.uc.timeline;

import com.zoe.commons.dao.orm.PageList;
import com.zoe.commons.dao.orm.lite.LiteOrm;
import com.zoe.commons.dao.orm.lite.LiteQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author lpw
 */
@Repository(TimelineModel.NAME + ".dao")
class TimelineDaoImpl implements TimelineDao {
    @Autowired
    protected LiteOrm liteOrm;

    @Override
    public void save(TimelineModel timeline) {
        liteOrm.save(timeline);
    }

    @Override
    public PageList<TimelineModel> query(String home, boolean sort) {
        return liteOrm.query(new LiteQuery(TimelineModel.class).where("c_home=?").order(sort ? "c_sort" : "c_start"), new Object[]{home});
    }
}
