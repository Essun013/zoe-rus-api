package com.zoe.rus.uc.user;

import com.zoe.commons.ctrl.validate.ValidateWrapper;
import com.zoe.commons.ctrl.validate.ValidatorSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @author lpw
 */
@Controller(UserService.VALIDATOR_SIGN_IN)
public class SignInValidatorImpl extends ValidatorSupport {
    @Autowired
    protected UserService userService;

    @Override
    protected String getDefaultFailureMessageKey() {
        return UserModel.NAME + ".need-sign-in";
    }

    @Override
    public boolean validate(ValidateWrapper validate, String parameter) {
        return userService.get() != null;
    }

    @Override
    public int getFailureCode(ValidateWrapper validate) {
        return UserCtrl.CODE + 91;
    }
}
