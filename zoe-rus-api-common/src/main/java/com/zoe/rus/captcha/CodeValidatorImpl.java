package com.zoe.rus.captcha;

import com.zoe.commons.ctrl.validate.ValidateWrapper;
import com.zoe.commons.ctrl.validate.ValidatorSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @auth lpw
 */
@Controller(CaptchaService.VALIDATOR_CODE)
public class CodeValidatorImpl extends ValidatorSupport {
    @Autowired
    protected CaptchaService captchaService;

    @Override
    public boolean validate(ValidateWrapper validate, String parameter) {
        return captchaService.check(parameter);
    }

    @Override
    protected String getDefaultFailureMessageKey() {
        return CaptchaModel.NAME + ".code.illegal";
    }
}
