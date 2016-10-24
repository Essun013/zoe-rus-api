package com.zoe.rus.kb.knowledge;

import com.zoe.commons.dao.orm.lite.LiteOrm;
import com.zoe.commons.dao.orm.lite.LiteQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author lpw
 */
@Repository(KnowledgeModel.NAME + ".dao")
class KnowledgeDaoImpl implements KnowledgeDao {
    @Autowired
    protected LiteOrm liteOrm;

    @Override
    public void delete() {
        liteOrm.delete(new LiteQuery(KnowledgeModel.class), null);
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
}
