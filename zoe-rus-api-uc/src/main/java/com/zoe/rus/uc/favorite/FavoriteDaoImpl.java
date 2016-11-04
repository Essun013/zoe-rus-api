package com.zoe.rus.uc.favorite;

import com.zoe.commons.dao.orm.PageList;
import com.zoe.commons.dao.orm.lite.LiteOrm;
import com.zoe.commons.dao.orm.lite.LiteQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author lpw
 */
@Repository(FavoriteModel.NAME + ".dao")
class FavoriteDaoImpl implements FavoriteDao {
    @Autowired
    protected LiteOrm liteOrm;

    @Override
    public FavoriteModel find(String user, String goal) {
        return liteOrm.findOne(new LiteQuery(FavoriteModel.class).where("c_user=? and c_goal=?"), new Object[]{user, goal});
    }

    @Override
    public void save(FavoriteModel favorite) {
        liteOrm.save(favorite);
    }

    @Override
    public void delete(FavoriteModel favorite) {
        liteOrm.delete(favorite);
    }

    @Override
    public PageList<FavoriteModel> query(String user, int type, int pageSize, int pageNum) {
        return liteOrm.query(new LiteQuery(FavoriteModel.class).where("c_user=? and c_type=?").order("c_time desc").size(pageSize).page(pageNum), new Object[]{user, type});
    }
}
