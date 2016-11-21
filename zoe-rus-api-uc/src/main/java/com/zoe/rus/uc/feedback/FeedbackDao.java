package com.zoe.rus.uc.feedback;

import com.zoe.commons.dao.orm.PageList;

/**
 * @author lpw
 */
interface FeedbackDao {
    void save(FeedbackModel feedback);

    PageList<FeedbackModel> query(int pageSize, int pageNum);
}
