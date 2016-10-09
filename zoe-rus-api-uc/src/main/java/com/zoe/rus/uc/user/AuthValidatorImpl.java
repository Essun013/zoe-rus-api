package com.zoe.rus.uc.user;

import com.zoe.commons.ctrl.context.Request;
import com.zoe.commons.ctrl.validate.ValidateWrapper;
import com.zoe.commons.ctrl.validate.ValidatorSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @author lpw
 */
@Controller(UserService.VALIDATOR_AUTH)
public class AuthValidatorImpl extends ValidatorSupport {
    @Autowired
    protected Request request;
    @Autowired
    protected UserService userService;

    @Override
    protected String getDefaultFailureMessageKey() {
        return UserModel.NAME+".auth.failure";
    }

    @Override
    public boolean validate(ValidateWrapper validate, String parameter) {
        return userService.auth(request.get("username"), request.get("password"));
    }
}
