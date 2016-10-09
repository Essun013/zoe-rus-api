package com.zoe.rus.uc.user;

/**
 * @author lpw
 */
interface UserDao {
    UserModel findById(String id);

    void save(UserModel user);

    void rollback();
}
