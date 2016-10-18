package com.zoe.rus.uc.auth;

/**
 * @author lpw
 */
public interface AuthService {
    /**
     * 用户名合法验证器。
     */
    String VALIDATOR_USERNAME_VALID = AuthModel.NAME + ".validator.username-valid";

    /**
     * 用户名不存在验证器。
     */
    String VALIDATOR_USERNAME_NOT_EXISTS = AuthModel.NAME + ".validator.username-not-exists";

    /**
     * 用户名存在验证器。
     */
    String VALIDATOR_USERNAME_EXISTS = AuthModel.NAME + ".validator.username-exists";

    /**
     * 查找认证信息。
     *
     * @param username 用户名。
     * @return 认证信息；如果不存在则返回null。
     */
    AuthModel findByUsername(String username);

    /**
     * 创建用户认证信息。
     *
     * @param user     用户ID。
     * @param username 用户名。
     * @param type     类型。
     * @return 如果创建成功则返回true；否则返回false。
     */
    boolean create(String user, String username, int type);

    void signIn(String macId,String user);
}
