package com.zoe.rus.classify;

import java.util.List;
import java.util.Set;

/**
 * @author lpw
 */
public interface ClassifyService {
    String EXISTS_VALIDATOR = ClassifyModel.NAME + ".validator.exists";

    void save(String id, String key, String parent, int sort, String name);

    List<ClassifyModel> query(String key, String parent);

    void delete(String key);

    void save(ClassifyModel classify);

    ClassifyModel findById(String id);

    Set<String> parent(String id);

    Set<String> children(String id);

    ClassifyModel find(String key, int sort);

    List<ClassifyModel> find(String key, String[] names);

    void link(String name, String label);

    Set<String> links(String key,String name);
}
