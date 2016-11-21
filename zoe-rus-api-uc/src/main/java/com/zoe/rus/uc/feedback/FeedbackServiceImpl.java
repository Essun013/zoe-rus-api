package com.zoe.rus.uc.feedback;

import com.zoe.rus.uc.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * @author lpw
 */
@Service(FeedbackModel.NAME + ".service")
public class FeedbackServiceImpl implements FeedbackService {
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
}
