package com.zoe.rus.classify;

import java.util.List;

/**
 * @author lpw
 */
public interface ClassifyService {
    void save(String id, String key, String parent, int sort, String name);

    List<ClassifyModel> query(String key, String parent);
}
