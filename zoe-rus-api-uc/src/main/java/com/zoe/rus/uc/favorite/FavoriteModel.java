package com.zoe.rus.uc.favorite;

import com.zoe.commons.dao.model.Jsonable;
import com.zoe.commons.dao.model.TephraModelSupport;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * @author lpw
 */
@Component(FavoriteModel.NAME + ".model")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Entity(name = FavoriteModel.NAME)
@Table(name = "t_uc_favorite")
public class FavoriteModel extends TephraModelSupport {
    static final String NAME = "rus.uc.favorite";

    private String user; // 用户
    private int type; // 类型
    private String goal; // 目标
    private Timestamp time; // 时间

    @Jsonable
    @Column(name = "c_user")
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
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
    @Column(name = "c_goal")
    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    @Jsonable
    @Column(name = "c_time")
    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }
}
