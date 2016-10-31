package com.zoe.rus.kb.knowledge;

import com.zoe.commons.dao.orm.PageList;

import java.util.Set;

/**
 * @author lpw
 */
interface KnowledgeDao {
    void delete(Set<String> ignore);

    void save(KnowledgeModel knowledge);

    KnowledgeModel findById(String id);

    KnowledgeModel findBySubject(String classify, String subject);

    PageList<KnowledgeModel> query(Set<String> classifies, int day, int pageSize, int pageNum);
}
