package com.zoe.rus.classify;

import net.sf.json.JSONArray;

import java.util.List;

/**
 * @author lpw
 */
public interface ClassifyService {
    void save(String id, String key, String parent, int sort, String name);

    List<ClassifyModel> query(String key, String parent);

    void delete(String key);

    void save(ClassifyModel classify);

    JSONArray query(String key);

    ClassifyModel findById(String id);
}
