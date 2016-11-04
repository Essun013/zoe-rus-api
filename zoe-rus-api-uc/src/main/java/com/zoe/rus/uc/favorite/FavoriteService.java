package com.zoe.rus.uc.favorite;

import net.sf.json.JSONObject;

/**
 * @author lpw
 */
public interface FavoriteService {
    void save(int type, String goal);

    void delete(String goal);

    JSONObject query(int type);
}
