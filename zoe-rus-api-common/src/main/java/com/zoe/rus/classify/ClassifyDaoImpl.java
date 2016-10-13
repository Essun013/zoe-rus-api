package com.zoe.rus.classify;

import com.zoe.commons.dao.orm.PageList;
import com.zoe.commons.dao.orm.lite.LiteOrm;
import com.zoe.commons.dao.orm.lite.LiteQuery;
import com.zoe.commons.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author lpw
 */
@Repository(ClassifyModel.NAME + ".dao")
class ClassifyDaoImpl implements ClassifyDao {
    @Autowired
    protected Validator validator;
    @Autowired
    protected LiteOrm liteOrm;

    @Override
    public PageList<ClassifyModel> root(String key) {
        return liteOrm.query(new LiteQuery(ClassifyModel.class).where("c_key=? and c_parent is null").order("c_sort"), new Object[]{key});
    }

    @Override
    public PageList<ClassifyModel> children(String parent) {
        return liteOrm.query(new LiteQuery(ClassifyModel.class).where("c_parent=?").order("c_sort"), new Object[]{parent});
    }

    @Override
    public ClassifyModel findById(String id) {
        return liteOrm.findById(ClassifyModel.class, id);
    }

    @Override
    public void save(ClassifyModel classify) {
        liteOrm.save(classify);
    }

    @Override
    public void delete(String key) {
        liteOrm.delete(new LiteQuery(ClassifyModel.class).where("c_key=?"), new Object[]{key});
    }
}
