package com.zoe.rus.uc.user;

import com.zoe.commons.ctrl.context.Request;
import com.zoe.commons.ctrl.execute.Execute;
import com.zoe.commons.ctrl.template.Templates;
import com.zoe.commons.ctrl.validate.Validate;
import com.zoe.commons.ctrl.validate.Validators;
import com.zoe.commons.dao.model.ModelHelper;
import com.zoe.commons.util.Message;
import com.zoe.rus.captcha.CaptchaService;
import com.zoe.rus.uc.auth.AuthService;
import com.zoe.rus.uc.home.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @author lpw
 */
@Controller(UserModel.NAME + ".ctrl")
@Execute(name = "/uc/user/", code = "41")
public class UserCtrl {
    private static final int CODE = 4100;

    @Autowired
    protected Message message;
    @Autowired
    protected ModelHelper modelHelper;
    @Autowired
    protected Request request;
    @Autowired
    protected Templates templates;
    @Autowired
    protected UserService userService;

    /**
     * 注册。
     * username 用户名。
     * password 密码。
     * name 姓名。
     * nick 昵称。
     * gender 性别：0-未知；1-男；2-女。
     * captcha 验证码。
     *
     * @return see UserModel
     */
    @Execute(name = "sign-up", validates = {
            @Validate(validator = Validators.NOT_EMPTY, parameter = "username", failureCode = 1),
            @Validate(validator = Validators.MAX_LENGTH, parameter = "username", number = {100}, failureCode = 2),
            @Validate(validator = Validators.NOT_EMPTY, parameter = "password", failureCode = 3),
            @Validate(validator = Validators.MAX_LENGTH, parameter = "name", number = {100}, failureCode = 5),
            @Validate(validator = Validators.MAX_LENGTH, parameter = "nick", number = {100}, failureCode = 6),
            @Validate(validator = Validators.BETWEEN, parameter = "gender", number = {0, 2}, failureCode = 7),
            @Validate(validator = AuthService.VALIDATOR_USERNAME_VALID, parameter = "username", failureCode = 4),
            @Validate(validator = CaptchaService.VALIDATOR_CODE, parameter = "captcha", failureCode = 9),
            @Validate(validator = AuthService.VALIDATOR_USERNAME_NOT_EXISTS, parameter = "username", failureCode = 21)
    })
    public Object signUp() {
        UserModel user = userService.signUp(request.get("username"), request.get("password"), request.get("name"), request.get("nick"), request.getAsInt("gender"));
        if (user == null)
            return templates.get().failure(CODE + 29, message.get(UserModel.NAME + ".sign-up.failure"), null, null);

        return modelHelper.toJson(user);
    }

    /**
     * 登入。
     * username 用户名。
     * password 密码。
     *
     * @return see UserModel
     */
    @Execute(name = "sign-in", validates = {
            @Validate(validator = Validators.NOT_EMPTY, parameter = "username", failureCode = 1),
            @Validate(validator = Validators.NOT_EMPTY, parameter = "password", failureCode = 3),
            @Validate(validator = UserService.VALIDATOR_AUTH, parameter = "password", failureCode = 22)
    })
    public Object signIn() {
        return sign();
    }

    /**
     * 获取当前登入用户信息。
     *
     * @return see UserModel
     */
    @Execute(name = "sign", validates = {
            @Validate(validator = UserService.VALIDATOR_SIGN_IN, failureCode = 11)
    })
    public Object sign() {
        UserModel user = userService.get();

        return user == null ? "" : modelHelper.toJson(user);
    }


    /**
     * 修改密码。
     * oldPassword 旧密码。
     * newPassword 新密码。
     *
     * @return {}
     */
    @Execute(name = "password", validates = {
            @Validate(validator = Validators.NOT_EMPTY, parameter = "oldPassword", failureCode = 2, failureArgKeys = {UserModel.NAME + ".password.old"}),
            @Validate(validator = Validators.NOT_EMPTY, parameter = "newPassword", failureCode = 2, failureArgKeys = {UserModel.NAME + ".password.new"}),
            @Validate(validator = Validators.NOT_EQUALS, parameter = "newPassword,oldPassword", failureCode = 2, failureArgKeys = {UserModel.NAME + ".password.new", UserModel.NAME + ".password.old"}),
            @Validate(validator = UserService.VALIDATOR_SIGN_IN, failureCode = 11)
    })
    public Object password() {
        if (userService.password(request.get("oldPassword"), request.get("newPassword")))
            return templates.get().success(null, UserModel.NAME + ".password.modify");

        return templates.get().failure(CODE + 22, message.get(UserModel.NAME + ".password.modify.failure"), "oldPassword", null);
    }

    /**
     * 修改用户信息。
     * name 姓名。
     * nick 姓名。
     * gender 性别。
     * address 详细地址。
     * birthday 出生日期。
     *
     * @return {}。
     */
    @Execute(name = "modify", validates = {
            @Validate(validator = Validators.MAX_LENGTH, parameter = "name", number = {100}, failureCode = 5),
            @Validate(validator = Validators.MAX_LENGTH, parameter = "nick", number = {100}, failureCode = 6),
            @Validate(validator = Validators.BETWEEN, parameter = "gender", number = {0, 2}, failureCode = 7),
            @Validate(validator = Validators.MAX_LENGTH, parameter = "address", number = {100}, failureCode = 8),
            @Validate(validator = UserService.VALIDATOR_SIGN_IN, failureCode = 11)
    })
    public Object modify() {
        userService.modify(request.setToModel(new UserModel()));

        return templates.get().success(null, UserModel.NAME + ".modify.success");
    }

    /**
     * 加入家庭。
     * code 家庭编号。
     *
     * @return ""。
     */
    @Execute(name = "home", validates = {
            @Validate(validator = UserService.VALIDATOR_SIGN_IN, failureCode = 11),
            @Validate(validator = HomeService.VALIDATOR_EXISTS_CODE, parameter = "code", failureCode = 12)
    })
    public Object home() {
        userService.home(request.get("code"));

        return "";
    }

    /**
     * 退出登入。
     *
     * @return ""
     */
    @Execute(name = "sign-out")
    public Object signOut() {
        userService.signOut();

        return "";
    }
}
