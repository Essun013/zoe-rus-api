package com.zoe.rus.kb.knowledge;

import net.sf.json.JSONObject;

/**
 * @author lpw
 */
public interface KnowledgeService {
    String PATH = "/kb";

    JSONObject get(String id, boolean html);

    JSONObject find(String subject, boolean html);

    String getHtml(String id);

    void reload();
}
