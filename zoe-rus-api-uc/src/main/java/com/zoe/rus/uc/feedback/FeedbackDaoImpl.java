package com.zoe.rus.uc.feedback;

import com.zoe.commons.dao.orm.PageList;
import com.zoe.commons.dao.orm.lite.LiteOrm;
import com.zoe.commons.dao.orm.lite.LiteQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author lpw
 */
@Repository(FeedbackModel.NAME + ".dao")
class FeedbackDaoImpl implements FeedbackDao {
    @Autowired
    protected LiteOrm liteOrm;

    @Override
    public void save(FeedbackModel feedback) {
        liteOrm.save(feedback);
    }

    @Override
    public PageList<FeedbackModel> query(int pageSize, int pageNum) {
        return liteOrm.query(new LiteQuery(FeedbackModel.class).order("c_time desc").size(pageSize).page(pageNum), null);
    }
}
