package com.zoe.rus.kb.keyword;

import net.sf.json.JSONArray;

import java.util.Map;
import java.util.Set;

/**
 * @author lpw
 */
public interface KeyWordService {
    JSONArray words();

    void save(Map<String,Set<String>> map);
}
