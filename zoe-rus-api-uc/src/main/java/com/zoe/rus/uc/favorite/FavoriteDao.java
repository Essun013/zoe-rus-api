package com.zoe.rus.uc.favorite;

import com.zoe.commons.dao.orm.PageList;

/**
 * @author lpw
 */
interface FavoriteDao {
    FavoriteModel find(String user, String goal);

    void save(FavoriteModel favorite);

    void delete(FavoriteModel favorite);

    PageList<FavoriteModel> query(String user, int type, int pageSize, int pageNum);
}
