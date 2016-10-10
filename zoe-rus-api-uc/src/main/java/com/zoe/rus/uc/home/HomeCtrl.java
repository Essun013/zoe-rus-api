package com.zoe.rus.uc.home;

import com.zoe.commons.ctrl.context.Request;
import com.zoe.commons.ctrl.execute.Execute;
import com.zoe.commons.ctrl.validate.Validate;
import com.zoe.commons.dao.model.ModelHelper;
import com.zoe.rus.uc.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @author lpw
 */
@Controller(HomeModel.NAME + ".ctrl")
@Execute(name = "/uc/home/", key = HomeModel.NAME, code = "40")
public class HomeCtrl {
    @Autowired
    protected ModelHelper modelHelper;
    @Autowired
    protected Request request;
    @Autowired
    protected HomeService homeService;

    /**
     * 获取家庭信息。
     *
     * @return {HomeModel}。
     */
    @Execute(name = "get", validates = {
            @Validate(validator = UserService.VALIDATOR_SIGN_IN, failureCode = 1),
            @Validate(validator = HomeService.VALIDATOR_EXISTS, failureCode = 2)
    })
    public Object get() {
        return modelHelper.toJson(homeService.get());
    }
}
