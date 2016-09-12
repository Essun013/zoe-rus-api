package com.zoe.rus.model;

import com.zoe.commons.dao.model.Jsonable;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Model支持类，主键ID使用UUID。
 *
 * @author lpw
 */
@MappedSuperclass()
public class ModelSupport extends com.zoe.commons.dao.model.ModelSupport {
    private static final String ID = "c_id";
    private static final String UUID = "uuid2";

    private String id;

    @Jsonable
    @Column(name = ID)
    @Id
    @GeneratedValue(generator = UUID)
    @GenericGenerator(name = UUID, strategy = UUID)
    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}
