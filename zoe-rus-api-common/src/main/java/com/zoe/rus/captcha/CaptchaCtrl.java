package com.zoe.rus.captcha;

import com.zoe.commons.ctrl.execute.Execute;
import com.zoe.commons.ctrl.validate.Validate;
import com.zoe.commons.ctrl.validate.Validators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @auth lpw
 */
@Controller(CaptchaModel.NAME + ".ctrl")
@Execute(name = "/captcha/", key = CaptchaModel.NAME, code = "11")
public class CaptchaCtrl {
    @Autowired
    protected CaptchaService captchaService;

    /**
     * 发送短信验证码。
     *
     * @return true/false。
     */
    @Execute(name = "mobile", validates = {
            @Validate(validator = Validators.MATCH_REGEX, parameter = "mobile", string = {"^1\\d{10}$"}, failureCode = 1, failureKey = CaptchaModel.NAME + ".mobile.illegal")
    })
    public Object mobile() {
        return captchaService.send(CaptchaService.Type.Mobile);
    }
}
