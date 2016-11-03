package com.zoe.rus.kb.knowledge;

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
@Repository(KnowledgeModel.NAME + ".dao")
class KnowledgeDaoImpl implements KnowledgeDao {
    @Autowired
    protected Validator validator;
    @Autowired
    protected LiteOrm liteOrm;

    @Override
    public void delete(Set<String> ignore) {
        if (validator.isEmpty(ignore)) {
            liteOrm.delete(new LiteQuery(KnowledgeModel.class), null);

            return;
        }

        StringBuilder where = new StringBuilder("c_id not in(");
        List<Object> args = new ArrayList<>();
        ignore.forEach(id -> {
            if (args.size() > 0)
                where.append(',');
            where.append('?');
            args.add(id);
        });
        liteOrm.delete(new LiteQuery(KnowledgeModel.class).where(where.append(')').toString()), args.toArray());
    }

    @Override
    public void save(KnowledgeModel knowledge) {
        liteOrm.save(knowledge);
    }

    @Override
    public KnowledgeModel findById(String id) {
        return liteOrm.findById(KnowledgeModel.class, id);
    }

    @Override
    public KnowledgeModel findBySubject(String classify, String subject) {
        return liteOrm.findOne(new LiteQuery(KnowledgeModel.class).where("c_classify=? and c_subject=?"), new Object[]{classify, subject});
    }

    @Override
    public PageList<KnowledgeModel> query(Set<String> classifies, int day, int pageSize, int pageNum) {
        if (classifies.isEmpty())
            return liteOrm.query(new LiteQuery(KnowledgeModel.class).where("c_start<=? and c_end>=?").order("c_sort").size(pageSize).page(pageNum), new Object[]{day, day});

        StringBuilder where = new StringBuilder("c_classify in(");
        List<Object> args = new ArrayList<>();
        classifies.forEach(classify -> {
            if (!args.isEmpty())
                where.append(',');
            where.append('?');
            args.add(classify);
        });
        where.append(") and c_start<=? and c_end>=?");
        args.add(day);
        args.add(day);

        return liteOrm.query(new LiteQuery(KnowledgeModel.class).where(where.toString()).order("c_sort").size(pageSize).page(pageNum), args.toArray());
    }
}
