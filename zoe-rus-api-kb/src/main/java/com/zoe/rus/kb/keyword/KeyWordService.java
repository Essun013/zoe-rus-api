package com.zoe.rus.kb.keyword;

import net.sf.json.JSONArray;

/**
 * @author lpw
 */
public interface KeyWordService {
    JSONArray words();

    void save(String words, String knowledge);
}
