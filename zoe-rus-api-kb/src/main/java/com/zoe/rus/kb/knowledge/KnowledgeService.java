package com.zoe.rus.kb.knowledge;

/**
 * @author lpw
 */
public interface KnowledgeService {
    String PATH = "/kb";

    String get(String id);

    String find(String subject);

    void reload();
}
