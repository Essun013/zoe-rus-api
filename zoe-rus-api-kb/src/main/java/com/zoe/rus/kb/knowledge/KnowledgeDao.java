package com.zoe.rus.kb.knowledge;

/**
 * @author lpw
 */
interface KnowledgeDao {
    void delete();

    void save(KnowledgeModel knowledge);

    KnowledgeModel findById(String id);

    KnowledgeModel findBySubject(String classify, String subject);
}
