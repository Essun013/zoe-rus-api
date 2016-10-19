package com.zoe.rus.uc.user;

/**
 * @author lpw
 */
public interface UserService {
    /**
     * 用户是否已登入验证器。
     */
    String VALIDATOR_SIGN_IN = UserModel.NAME + ".validator.sign-in";

    /**
     * 注册用户。
     *
     * @param username 用户名。
     * @param password 密码。
     * @param name     姓名。
     * @param nick     昵称。
     * @param gender   性别。
     * @param auto     是否自动注册。
     * @return 用户信息。
     */
    UserModel signUp(String username, String password, String name, String nick, int gender, boolean auto);

    /**
     * 登入。
     *
     * @param username 用户名。
     * @param password 密码。
     * @param macId    MacID。
     * @return 如果验证通过则返回用户信息，否则返回null。
     */
    UserModel signIn(String username, String password, String macId);

    /**
     * 获取当前用户。
     *
     * @return 获取当前用户；如果未登入则返回null。
     */
    UserModel get();

    /**
     * 修改密码。
     *
     * @param oldPassword 旧密码。
     * @param newPassword 新密码。
     * @return 如果修改成功则返回true；否则返回false。
     */
    boolean password(String oldPassword, String newPassword);

    /**
     * 修改头像。
     *
     * @param uri 头像图片地址。
     */
    void portrait(String uri);

    /**
     * 修改用户信息。
     *
     * @param model 用户信息。
     */
    void modify(UserModel model);

    void home(String code);

    /**
     * 退出登入。
     */
    void signOut();
}
