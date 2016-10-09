package com.zoe.rus.uc.auth;

/**
 * @author lpw
 */
interface AuthDao {
    AuthModel findByUsername(String username);

    void save(AuthModel auth);
}
