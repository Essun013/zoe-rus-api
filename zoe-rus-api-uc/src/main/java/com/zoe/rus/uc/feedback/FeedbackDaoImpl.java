package com.zoe.rus.uc.feedback;

import com.zoe.commons.dao.orm.lite.LiteOrm;
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
}
