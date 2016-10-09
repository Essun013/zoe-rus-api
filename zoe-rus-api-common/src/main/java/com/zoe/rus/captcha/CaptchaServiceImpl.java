package com.zoe.rus.captcha;

import org.springframework.stereotype.Service;

/**
 * @auth lpw
 */
@Service(CaptchaModel.NAME + ".service")
public class CaptchaServiceImpl implements CaptchaService {
    @Override
    public boolean send(Type type) {
        if (type == Type.Mobile) {
            return true;
        }

        return false;
    }

    @Override
    public boolean check(String code) {
        return true;
    }
}
