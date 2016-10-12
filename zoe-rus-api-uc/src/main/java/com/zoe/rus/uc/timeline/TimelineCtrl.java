package com.zoe.rus.uc.timeline;

import com.zoe.commons.ctrl.context.Request;
import com.zoe.commons.ctrl.execute.Execute;
import com.zoe.commons.ctrl.template.Templates;
import com.zoe.commons.ctrl.validate.Validate;
import com.zoe.commons.ctrl.validate.Validators;
import com.zoe.commons.util.Message;
import com.zoe.rus.uc.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @author lpw
 */
@Controller(TimelineModel.NAME + ".ctrl")
@Execute(name = "/uc/timeline/", key = TimelineModel.NAME, code = "42")
public class TimelineCtrl {
    @Autowired
    protected Message message;
    @Autowired
    protected Request request;
    @Autowired
    protected Templates templates;
    @Autowired
    protected TimelineService timelineService;

    /**
     * 创建时间轴。
     * lmp 末次月经日期。
     * childbirth 预产期。
     * birthday 宝宝生日。
     *
     * @return ""。
     */
    @Execute(name = "create", validates = {
            @Validate(validator = Validators.NOT_EMPTY, parameters = {"lmp", " childbirth", "birthday"}, failureCode = 1, failureKey = TimelineModel.NAME + ".lmp-childbirth-birthday.empty"),
            @Validate(validator = UserService.VALIDATOR_SIGN_IN, failureCode = 2)
    })
    public Object create() {
        return timelineService.create(request.getAsDate("lmp"), request.getAsDate("childbirth"), request.getAsDate("birthday")) ? "" :
                templates.get().failure(4201, message.get(TimelineModel.NAME + ".lmp-childbirth-birthday.empty"), null, null);
    }
}
