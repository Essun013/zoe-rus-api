package com.zoe.rus.uc.feedback;

import com.zoe.commons.dao.model.ModelHelper;
import com.zoe.rus.uc.user.UserService;
import com.zoe.rus.util.Pagination;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * @author lpw
 */
@Service(FeedbackModel.NAME + ".service")
public class FeedbackServiceImpl implements FeedbackService {
    @Autowired
    protected ModelHelper modelHelper;
    @Autowired
    protected Pagination pagination;
    @Autowired
    protected UserService userService;
    @Autowired
    protected FeedbackDao feedbackDao;

    @Override
    public void save(String content) {
        FeedbackModel feedback = new FeedbackModel();
        feedback.setUser(userService.get().getId());
        feedback.setContent(content);
        feedback.setTime(new Timestamp(System.currentTimeMillis()));
        feedbackDao.save(feedback);
    }

    @Override
    public JSONObject query() {
        JSONObject json = feedbackDao.query(pagination.getPageSize(), pagination.getPageNum()).toJson();
        JSONArray array = json.getJSONArray("list");
        for (int i = 0, size = array.size(); i < size; i++) {
            JSONObject object = array.getJSONObject(i);
            object.put("user", modelHelper.toJson(userService.findById(object.getString("user"))));
        }

        return json;
    }
}
