package com.zoe.rus.kb.knowledge;

import com.zoe.commons.dao.orm.PageList;
import net.sf.json.JSONObject;

/**
 * @author lpw
 */
public interface KnowledgeService {
    enum Favorite {Add, Remove}

    String PATH = "/kb";

    JSONObject get(String id);

    JSONObject find(String subject);

    String getHtml(String id);

    JSONObject query(String[] classify, int day, boolean image);

    void favorite(String id, Favorite favorite);

    PageList<KnowledgeModel> search(String keyword);

    void reload();
}
