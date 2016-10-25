package com.zoe.rus.kb.knowledge;

import com.zoe.commons.dao.orm.PageList;

/**
 * @author lpw
 */
interface KnowledgeDao {
    void delete();

    void save(KnowledgeModel knowledge);

    KnowledgeModel findById(String id);

    KnowledgeModel findBySubject(String classify, String subject);

    PageList<KnowledgeModel> query(String classify, int day, int pageSize, int pageNum);
}
