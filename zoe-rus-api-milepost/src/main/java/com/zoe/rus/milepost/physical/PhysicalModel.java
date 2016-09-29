package com.zoe.rus.milepost.physical;

import com.zoe.commons.dao.model.Jsonable;
import com.zoe.rus.model.ModelSupport;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author lpw
 */
@Component(PhysicalModel.NAME + ".model")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Entity(name = PhysicalModel.NAME)
@Table(name = "t_kb_physical")
public class PhysicalModel extends ModelSupport {
    static final String NAME = "kb.physical";

    private String region; // 区域
    private String hospital; // 医院
    private int type; // 类型
    private String time; // 时间
    private String content; // 内容
    private String knowledge; // 知识

    @Jsonable
    @Column(name = "c_region")
    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @Jsonable
    @Column(name = "c_hospital")
    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    @Jsonable
    @Column(name = "c_type")
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Jsonable
    @Column(name = "c_time")
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Jsonable
    @Column(name = "c_content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Jsonable
    @Column(name = "c_knowledge")
    public String getKnowledge() {
        return knowledge;
    }

    public void setKnowledge(String knowledge) {
        this.knowledge = knowledge;
    }
}
