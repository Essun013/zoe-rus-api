package com.zoe.rus.kb.hospital;

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
@Component(HospitalModel.NAME + ".model")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Entity(name = HospitalModel.NAME)
@Table(name = "t_kb_hospital")
public class HospitalModel extends ModelSupport {
    static final String NAME = "rus.kb.hospital";

    private String region; // 地区ID
    private String name; // 名称
    private String address; // 地址
    private String longitude; // 经度
    private String latitude; // 纬度

    @Jsonable
    @Column(name = "c_region")
    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
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
    @Column(name = "c_address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Jsonable
    @Column(name = "c_longitude")
    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Jsonable
    @Column(name = "c_latitude")
    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
}
