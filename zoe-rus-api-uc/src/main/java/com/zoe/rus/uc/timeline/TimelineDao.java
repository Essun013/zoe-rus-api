package com.zoe.rus.uc.timeline;

import com.zoe.commons.dao.orm.PageList;

/**
 * @author lpw
 */
interface TimelineDao {
    void save(TimelineModel timeline);

    PageList<TimelineModel> query(String home, boolean sort);
}
