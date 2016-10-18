package com.zoe.rus.uc.timeline;

import com.zoe.commons.dao.mongo.Mongo;
import com.zoe.commons.dao.orm.PageList;
import com.zoe.commons.dao.orm.lite.LiteOrm;
import com.zoe.commons.dao.orm.lite.LiteQuery;
import net.sf.json.JSONObject;
import org.bson.Document;
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
        mongo.getCollection(null, "t_uc_timeline").insertOne(new Document(json));
    }
}
