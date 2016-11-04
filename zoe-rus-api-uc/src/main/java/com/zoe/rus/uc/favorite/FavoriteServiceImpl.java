package com.zoe.rus.uc.favorite;

import com.zoe.commons.ctrl.context.Session;
import com.zoe.commons.util.Generator;
import com.zoe.rus.kb.knowledge.KnowledgeService;
import com.zoe.rus.uc.user.UserService;
import com.zoe.rus.util.Pagination;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * @author lpw
 */
@Service(FavoriteModel.NAME + ".service")
public class FavoriteServiceImpl implements FavoriteService {
    private static final String SESSION_KEY = FavoriteModel.NAME + ".service.session:";
    private static final String SESSION_RANDOM = FavoriteModel.NAME + ".service.session.random:";

    @Autowired
    protected Generator generator;
    @Autowired
    protected Session session;
    @Autowired
    protected Pagination pagination;
    @Autowired
    protected KnowledgeService knowledgeService;
    @Autowired
    protected UserService userService;
    @Autowired
    protected FavoriteDao favoriteDao;

    @Override
    public void save(int type, String goal) {
        FavoriteModel favorite = favoriteDao.find(userService.get().getId(), goal);
        if (favorite != null)
            return;

        favorite = new FavoriteModel();
        favorite.setUser(userService.get().getId());
        favorite.setType(type);
        favorite.setGoal(goal);
        favorite.setTime(new Timestamp(System.currentTimeMillis()));
        favoriteDao.save(favorite);
        if (type == 1)
            knowledgeService.favorite(goal, KnowledgeService.Favorite.Add);
        resetRandom();
    }

    @Override
    public void delete(String goal) {
        FavoriteModel favorite = favoriteDao.find(userService.get().getId(), goal);
        if (favorite == null)
            return;

        if (favorite.getType() == 1)
            knowledgeService.favorite(goal, KnowledgeService.Favorite.Remove);
        favoriteDao.delete(favorite);
        resetRandom();
    }

    @Override
    public JSONObject query(int type) {
        int pageSize = pagination.getPageSize();
        int pageNum = pagination.getPageNum();
        String key = SESSION_KEY + getRandom() + type + pageSize + pageNum;
        JSONObject object = session.get(key);
        if (object == null) {
            object = favoriteDao.query(userService.get().getId(), type, pageSize, pageNum).toJson();
            if (type == 1)
                knowledge(object);
            session.set(key, object);
        }

        return object;
    }

    protected String getRandom() {
        String random = session.get(SESSION_RANDOM);

        return random == null ? resetRandom() : random;
    }

    protected String resetRandom() {
        String random = generator.random(32);
        session.set(SESSION_RANDOM, random);

        return random;
    }

    protected void knowledge(JSONObject json) {
        JSONArray array = new JSONArray();
        JSONArray list = json.getJSONArray("list");
        for (int i = 0, size = list.size(); i < size; i++)
            array.add(knowledgeService.get(list.getJSONObject(i).getString("goal")));
        json.put("list", array);
    }
}
