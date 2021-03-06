package com.zoe.rus.uc.timeline;

import com.zoe.commons.dao.mongo.Mongo;
import com.zoe.commons.dao.orm.PageList;
import com.zoe.commons.dao.orm.lite.LiteOrm;
import com.zoe.commons.dao.orm.lite.LiteQuery;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author lpw
 */
@Repository(TimelineModel.NAME + ".dao")
class TimelineDaoImpl implements TimelineDao {
    @Autowired
    protected LiteOrm liteOrm;
    @Autowired
    protected Mongo mongo;

    @Override
    public void save(TimelineModel timeline) {
        liteOrm.save(timeline);
    }

    @Override
    public PageList<TimelineModel> query(String home, boolean sort) {
        return liteOrm.query(new LiteQuery(TimelineModel.class).where("c_home=?").order(sort ? "c_sort" : "c_start"), new Object[]{home});
    }

    @Override
    public void insertPhysical(JSONObject json) {
        mongo.insert(TimelineModel.class, json);
    }

    @Override
    public JSONObject getPhysical(String id) {
        JSONObject where = new JSONObject();
        where.put("id", id);

        return mongo.findOne(TimelineModel.class, where);
    }

    @Override
    public void updatePhysical(String id, JSONObject object) {
        JSONObject where = new JSONObject();
        where.put("id", id);
        mongo.update(TimelineModel.class, object, where);
    }
}
