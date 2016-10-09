package com.zoe.rus.uc.auth;

import com.zoe.commons.ctrl.validate.ValidateWrapper;
import com.zoe.commons.ctrl.validate.ValidatorSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @author lpw
 */
@Controller(AuthService.VALIDATOR_USERNAME_EXISTS)
public class UsernameExistsValidatorImpl extends ValidatorSupport {
    @Autowired
    protected AuthService authService;

    @Override
    protected String getDefaultFailureMessageKey() {
        return AuthModel.NAME + ".username.not-exists";
    }

    @Override
    public boolean validate(ValidateWrapper validate, String parameter) {
        return authService.findUser(parameter, 0) != null;
    }
}
