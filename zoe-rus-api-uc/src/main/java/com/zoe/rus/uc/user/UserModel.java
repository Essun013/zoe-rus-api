package com.zoe.rus.uc.user;

import com.zoe.commons.dao.model.Jsonable;
import com.zoe.rus.model.ModelSupport;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import java.sql.Timestamp;
import java.sql.Date;

/**
 * @author lpw
 */
@Component(UserModel.NAME + ".model")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Entity(name = UserModel.NAME)
@Table(name = "t_uc_user")
public class UserModel extends ModelSupport {
    static final String NAME = "rus.uc.user";

    private String password; // 密码
    private String name; // 姓名
    private String nick; // 昵称
    private String mobile; // 手机号
    private String portrait; // 头像
    private int gender; // 性别：0-未知；1-男；2-女
    private String address; // 地址
    private Date birthday; // 出生日期
    private Timestamp register; // 注册时间

    @Jsonable
    @Column(name = "c_password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
    @Column(name = "c_nick")
    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    @Jsonable
    @Column(name = "c_mobile")
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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
    @Column(name = "c_gender")
    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
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
    @Column(name = "c_birthday")
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Jsonable
    @Column(name = "c_register")
    public Timestamp getRegister() {
        return register;
    }

    public void setRegister(Timestamp register) {
        this.register = register;
    }
}
