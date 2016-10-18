package com.zoe.rus.uc.auth;

import com.zoe.commons.util.Security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lpw
 */
@Service(AuthModel.NAME + ".service")
public class AuthServiceImpl implements AuthService {
    @Autowired
    protected Security security;
    @Autowired
    protected AuthDao authDao;

    @Override
    public AuthModel findByUsername(String username) {
        return authDao.findByUsername(username);
    }

    @Override
    public boolean create(String user, String username, int type) {
        if (authDao.findByUsername(username) != null)
            return false;

        AuthModel auth = new AuthModel();
        auth.setUser(user);
        auth.setUsername(username);
        auth.setType(type);
        authDao.save(auth);

        return true;
    }

    @Override
    public void signIn(String macId, String user) {
        AuthModel auth = findByUsername(macId);
        if (auth == null || auth.getUser().equals(user))
            return;

        auth.setUser(user);
        authDao.save(auth);
    }
}
