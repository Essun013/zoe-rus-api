package com.zoe.rus.classify;

import com.zoe.commons.dao.orm.PageList;
import com.zoe.commons.dao.orm.lite.LiteOrm;
import com.zoe.commons.dao.orm.lite.LiteQuery;
import com.zoe.commons.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
    public ClassifyModel findByKey(String key, String name) {
        return liteOrm.findOne(new LiteQuery(ClassifyModel.class).where("c_key=? and c_parent is null and c_name=?"), new Object[]{key, name});
    }

    @Override
    public ClassifyModel findByParent(String parent, String name) {
        return liteOrm.findOne(new LiteQuery(ClassifyModel.class).where("c_parent=? and c_name=?"), new Object[]{parent, name});
    }

    @Override
    public void save(ClassifyModel classify) {
        liteOrm.save(classify);
    }

    @Override
    public void delete(String key, Set<String> ignore) {
        if (validator.isEmpty(ignore)) {
            liteOrm.delete(new LiteQuery(ClassifyModel.class).where("c_key=?"), new Object[]{key});

            return;
        }

        StringBuilder where = new StringBuilder("c_key=? and c_id not in(");
        List<Object> args = new ArrayList<>();
        args.add(key);
        ignore.forEach(id -> {
            if (args.size() > 1)
                where.append(',');
            where.append('?');
            args.add(id);
        });
        liteOrm.delete(new LiteQuery(ClassifyModel.class).where(where.append(')').toString()), args.toArray());
    }

    @Override
    public void delete(ClassifyModel classify) {
        liteOrm.delete(classify);
    }
}
