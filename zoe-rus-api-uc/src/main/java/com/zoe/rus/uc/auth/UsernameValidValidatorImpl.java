package com.zoe.rus.uc.auth;

import com.zoe.commons.ctrl.validate.ValidateWrapper;
import com.zoe.commons.ctrl.validate.ValidatorSupport;
import com.zoe.commons.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @author lpw
 */
@Controller(AuthService.VALIDATOR_USERNAME_VALID)
public class UsernameValidValidatorImpl extends ValidatorSupport {
    @Autowired
    protected Validator validator;

    @Override
    protected String getDefaultFailureMessageKey() {
        return AuthModel.NAME + ".username.invalid";
    }

    @Override
    public boolean validate(ValidateWrapper validate, String parameter) {
        return validator.isMatchRegex("^1[0-9]{10}$", parameter);
    }
}
