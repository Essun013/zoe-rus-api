package com.zoe.rus.uc.timeline;

import com.zoe.commons.dao.orm.PageList;
import net.sf.json.JSONObject;

/**
 * @author lpw
 */
interface TimelineDao {
    void save(TimelineModel timeline);

    PageList<TimelineModel> query(String home, boolean sort);

    void insertPhysical(JSONObject json);

    JSONObject getPhysical(String id);
}
