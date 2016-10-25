package com.zoe.rus.kb.knowledge;

import net.sf.json.JSONObject;

/**
 * @author lpw
 */
public interface KnowledgeService {
    String PATH = "/kb";

    JSONObject get(String id);

    JSONObject find(String subject);

    String getHtml(String id);

    JSONObject query(String[] classify, int day);

    void reload();
}
