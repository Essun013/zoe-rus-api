package com.zoe.rus.uc.auth;

import com.zoe.commons.dao.model.Jsonable;
import com.zoe.commons.dao.model.TephraModelSupport;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author lpw
 */
@Component(AuthModel.NAME + ".model")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Entity(name = AuthModel.NAME)
@Table(name = "t_uc_auth")
public class AuthModel extends TephraModelSupport {
    static final String NAME = "rus.uc.auth";

    private String user; // 用户ID
    private String username; // 用户名
    private int type; // 类型：0-机器码；1-自有账号；其他为第三方

    @Jsonable
    @Column(name = "c_user")
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Jsonable
    @Column(name = "c_username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Jsonable
    @Column(name = "c_type")
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
