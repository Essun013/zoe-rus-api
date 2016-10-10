package com.zoe.rus.uc.home;

import com.zoe.commons.cache.Cache;
import com.zoe.commons.util.Generator;
import com.zoe.commons.util.Message;
import com.zoe.commons.util.Validator;
import com.zoe.rus.uc.user.UserModel;
import com.zoe.rus.uc.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * @author lpw
 */
@Service(HomeModel.NAME + ".service")
public class HomeServiceImpl implements HomeService {
    private static final String CACHE_ID = HomeModel.NAME + ".service.id:";
    private static final String CACHE_CODE = HomeModel.NAME + ".service.code:";

    @Autowired
    protected Generator generator;
    @Autowired
    protected Message message;
    @Autowired
    protected Validator validator;
    @Autowired
    protected Cache cache;
    @Autowired
    protected UserService userService;
    @Autowired
    protected HomeDao homeDao;

    @Override
    public String create() {
        HomeModel home = new HomeModel();
        home.setCode(generateCode());
        home.setName(message.get(HomeModel.NAME + ".name.default"));
        home.setRegister(new Timestamp(System.currentTimeMillis()));
        homeDao.save(home);

        return home.getId();
    }

    protected String generateCode() {
        for (; true; ) {
            String code = generator.random(8);
            if (homeDao.findByCode(code) == null)
                return code;
        }
    }

    @Override
    public HomeModel get() {
        UserModel user = userService.get();

        return user == null ? null : findById(user.getHome());
    }

    @Override
    public HomeModel findById(String id) {
        if (validator.isEmpty(id))
            return null;

        String key = CACHE_ID + id;
        HomeModel home = cache.get(key);
        if (home == null)
            cache.put(key, home = homeDao.findById(id), false);

        return home;
    }

    @Override
    public HomeModel findByCode(String code) {
        if (validator.isEmpty(code))
            return null;

        code = code.toLowerCase();
        String key = CACHE_CODE + code;
        HomeModel home = cache.get(key);
        if (home == null)
            cache.put(key, home = homeDao.findByCode(code), false);

        return home;
    }
}
