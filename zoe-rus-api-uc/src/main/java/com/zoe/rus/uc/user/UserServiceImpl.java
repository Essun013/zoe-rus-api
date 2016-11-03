package com.zoe.rus.uc.user;

import com.zoe.commons.crypto.Digest;
import com.zoe.commons.ctrl.context.Session;
import com.zoe.commons.freemarker.Freemarker;
import com.zoe.commons.util.Converter;
import com.zoe.commons.util.Generator;
import com.zoe.commons.util.Logger;
import com.zoe.commons.util.Message;
import com.zoe.commons.util.Validator;
import com.zoe.rus.uc.auth.AuthModel;
import com.zoe.rus.uc.auth.AuthService;
import com.zoe.rus.uc.home.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * @author lpw
 */
@Service(UserModel.NAME + ".service")
public class UserServiceImpl implements UserService {
    private static final String SESSION = UserModel.NAME + ".service.session";
    private static final String PASSWORD = UserModel.NAME + ".service.password";

    @Autowired
    protected Digest digest;
    @Autowired
    protected Validator validator;
    @Autowired
    protected Generator generator;
    @Autowired
    protected Message message;
    @Autowired
    protected Converter converter;
    @Autowired
    protected Logger logger;
    @Autowired
    protected Freemarker freemarker;
    @Autowired
    protected Session session;
    @Autowired
    protected HomeService homeService;
    @Autowired
    protected AuthService authService;
    @Autowired
    protected UserDao userDao;

    @Override
    public UserModel signUp(String username, String password, String name, String nick, int gender, boolean auto) {
        UserModel user = get();
        if (user == null)
            user = new UserModel();
        if (gender == 2 && validator.isEmpty(user.getHome()))
            user.setHome(homeService.create());
        if (!auto) {
            user.setMobile(username);
            user.setPassword(password(password));
            if (!validator.isEmpty(name))
                user.setName(name);
            if (!validator.isEmpty(nick))
                user.setNick(nick);
        }
        user.setGender(gender);
        if (user.getRegister() == null)
            user.setRegister(new Timestamp(System.currentTimeMillis()));
        userDao.save(user);
        if (!authService.create(user.getId(), username, 0)) {
            userDao.rollback();

            return null;
        }

        session.set(SESSION, user);

        return user;
    }

    @Override
    public UserModel signIn(String username, String password, String macId) {
        AuthModel auth = authService.findByUsername(username);
        if (auth == null)
            return null;

        UserModel user = userDao.findById(auth.getUser());
        if (user == null || (auth.getType() == 1 && !user.getPassword().equals(password(password))))
            return null;

        authService.signIn(macId, user.getId());
        session.set(SESSION, user);

        return user;
    }

    @Override
    public UserModel get() {
        return session.get(SESSION);
    }

    @Override
    public boolean password(String oldPassword, String newPassword) {
        UserModel user = get();
        if (user == null || !user.getPassword().equals(password(oldPassword)))
            return false;

        user.setPassword(password(newPassword));
        save(user);

        return true;
    }

    protected String password(String password) {
        return digest.md5(digest.md5(PASSWORD + password) + PASSWORD);
    }

    @Override
    public void portrait(String uri) {
        UserModel user = get();
        if (user == null)
            return;

        user.setPortrait(uri);
        save(user);
    }

    @Override
    public void modify(UserModel model) {
        UserModel user = get();
        if (user == null)
            return;

        if (!validator.isEmpty(model.getName()))
            user.setName(model.getName());
        if (!validator.isEmpty(model.getNick()))
            user.setNick(model.getNick());
        if (model.getGender() > 0)
            user.setGender(model.getGender());
        if (!validator.isEmpty(model.getAddress()))
            user.setAddress(model.getAddress());
        if (model.getBirthday() != null)
            user.setBirthday(model.getBirthday());
        save(user);
    }

    @Override
    public void home(String code) {
        UserModel user = get();
        user.setHome(homeService.findByCode(code).getCode());
        save(user);
    }

    private void save(UserModel user) {
        userDao.save(user);
        session.set(SESSION, user);
    }

    @Override
    public void signOut() {
        session.remove(SESSION);
    }
}
