package com.zoe.rus.uc.auth;

import com.zoe.commons.ctrl.validate.ValidateWrapper;
import com.zoe.commons.ctrl.validate.ValidatorSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @author lpw
 */
@Controller(AuthService.VALIDATOR_USERNAME_NOT_EXISTS)
public class UsernameNotExistsValidatorImpl extends ValidatorSupport {
    @Autowired
    protected AuthService authService;

    @Override
    protected String getDefaultFailureMessageKey() {
        return AuthModel.NAME + ".username.exists";
    }

    @Override
    public boolean validate(ValidateWrapper validate, String parameter) {
        return authService.findByUsername(parameter) == null;
    }
}
