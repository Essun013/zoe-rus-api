package com.zoe.rus.classify;

import com.zoe.commons.dao.model.Jsonable;
import com.zoe.rus.model.ModelSupport;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

/**
 * @author lpw
 */
@Component(ClassifyModel.NAME + ".model")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Entity(name = ClassifyModel.NAME)
@Table(name = "t_common_classify")
public class ClassifyModel extends ModelSupport implements Comparable<ClassifyModel> {
    static final String NAME = "rus.common.classify";

    private String key;
    private String parent;
    private int sort;
    private String name;
    private List<ClassifyModel> children;

    @Jsonable
    @Column(name = "c_key")
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Jsonable
    @Column(name = "c_parent")
    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    @Jsonable
    @Column(name = "c_sort")
    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    @Jsonable
    @Column(name = "c_name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ClassifyModel> getChildren() {
        return children;
    }

    public void setChildren(List<ClassifyModel> children) {
        this.children = children;
    }

    @Override
    public int compareTo(ClassifyModel o) {
        return sort - o.sort;
    }
}
