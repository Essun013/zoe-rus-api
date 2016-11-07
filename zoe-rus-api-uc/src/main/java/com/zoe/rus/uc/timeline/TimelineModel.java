package com.zoe.rus.uc.timeline;

import com.zoe.commons.dao.model.Jsonable;
import com.zoe.commons.dao.model.TephraModelSupport;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Date;

/**
 * @author lpw
 */
@Component(TimelineModel.NAME + ".model")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Entity(name = TimelineModel.NAME)
@Table(name = "t_uc_timeline")
public class TimelineModel extends TephraModelSupport {
    static final String NAME = "rus.uc.timeline";

    private String home; // 家庭
    private int sort; // 顺序
    private int type; // 类型：0-孕妈；1-宝宝
    private String name; // 名称
    private String portrait; // 头像
    private String region; // 地区
    private String hospital; // 医院
    private Date start; // 开始日期
    private int day; // 天数
    private int end; // 结束：0-进行中；1-已结束

    @Jsonable
    @Column(name = "c_home")
    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
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
    @Column(name = "c_type")
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Jsonable
    @Column(name = "c_name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Jsonable
    @Column(name = "c_portrait")
    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

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
    @Column(name = "c_start")
    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    @Jsonable
    @Column(name = "c_day")
    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    @Jsonable
    @Column(name = "c_end")
    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }
}
