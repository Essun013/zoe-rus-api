package com.zoe.rus.uc.feedback;

import com.zoe.commons.ctrl.context.Request;
import com.zoe.commons.ctrl.execute.Execute;
import com.zoe.commons.ctrl.validate.Validate;
import com.zoe.commons.ctrl.validate.Validators;
import com.zoe.rus.uc.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @author lpw
 */
@Controller(FeedbackModel.NAME + ".ctrl")
@Execute(name = "/uc/feedback/", key = FeedbackModel.NAME, code = "44")
public class FeedbackCtrl {
    @Autowired
    protected Request request;
    @Autowired
    protected FeedbackService feedbackService;

    /**
     * 保存反馈信息。
     * content 返回内容。
     *
     * @return ""。
     */
    @Execute(name = "save", validates = {
            @Validate(validator = Validators.NOT_EMPTY, parameter = "content", failureCode = 1),
            @Validate(validator = UserService.VALIDATOR_SIGN_IN, failureCode = 2)
    })
    public Object save() {
        feedbackService.save(request.get("content"));

        return "";
    }

    /**
     * 检索反馈信息。
     * pageSize 每页显示记录数。
     * pageNum 当前显示页数。
     *
     * @return {}。
     */
    @Execute(name = "query", validates = {@Validate(validator = Validators.SIGN)})
    public Object query() {
        return feedbackService.query();
    }
}
