package com.zoe.rus.captcha;

/**
 * 验证码。
 *
 * @auth lpw
 */
public interface CaptchaService {
    enum Type {Mobile}

    /**
     * 验证码验证器。
     */
    String VALIDATOR_CODE = CaptchaModel.NAME + ".validator.code";

    /**
     * 发送一个验证码。
     *
     * @param type 类型。
     * @return 如果发送成功则返回true；否则返回false。
     */
    boolean send(Type type);

    /**
     * 验证。
     *
     * @param code 验证码。
     * @return 如果验证成功则返回true；否则返回false。
     */
    boolean check(String code);
}
