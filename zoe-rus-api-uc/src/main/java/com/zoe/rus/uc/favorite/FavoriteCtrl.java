package com.zoe.rus.uc.favorite;

import com.zoe.commons.ctrl.context.Request;
import com.zoe.commons.ctrl.execute.Execute;
import com.zoe.commons.ctrl.validate.Validate;
import com.zoe.commons.ctrl.validate.Validators;
import com.zoe.rus.uc.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @author lpw
 */
@Controller(FavoriteModel.NAME + ".ctrl")
@Execute(name = "/uc/favorite/", key = FavoriteModel.NAME, code = "43")
public class FavoriteCtrl {
    @Autowired
    protected Request request;
    @Autowired
    protected FavoriteService favoriteService;

    /**
     * 检索收藏。
     * type 类型：1-知识。
     * pageSize 每页显示记录数。
     * pageNum 当前显示页数。
     *
     * @return {}。
     */
    @Execute(name = "query", validates = {
            @Validate(validator = Validators.BETWEEN, parameter = "type", number = {1, 1}, failureCode = 1),
            @Validate(validator = UserService.VALIDATOR_SIGN_IN, failureCode = 3)
    })
    public Object query() {
        return favoriteService.query(request.getAsInt("type"));
    }

    /**
     * 保存收藏。
     * type 类型：1-知识。
     * goal 目标ID。
     *
     * @return ""。
     */
    @Execute(name = "save", validates = {
            @Validate(validator = Validators.BETWEEN, parameter = "goal", number = {1, 1}, failureCode = 1),
            @Validate(validator = Validators.NOT_EMPTY, parameter = "goal", failureCode = 2),
            @Validate(validator = UserService.VALIDATOR_SIGN_IN, failureCode = 3)
    })
    public Object save() {
        favoriteService.save(request.getAsInt("type"), request.get("goal"));

        return "";
    }

    /**
     * 验证是否已收藏。
     * type 类型：1-知识。
     * goal 目标ID。
     *
     * @return true/false。
     */
    @Execute(name = "has", validates = {
            @Validate(validator = Validators.BETWEEN, parameter = "goal", number = {1, 1}, failureCode = 1),
            @Validate(validator = Validators.NOT_EMPTY, parameter = "goal", failureCode = 2),
            @Validate(validator = UserService.VALIDATOR_SIGN_IN, failureCode = 3)
    })
    public Object has() {
        return favoriteService.has(request.getAsInt("type"), request.get("goal"));
    }

    /**
     * 删除收藏。
     * goal 目标ID。
     *
     * @return ""。
     */
    @Execute(name = "delete", validates = {
            @Validate(validator = Validators.NOT_EMPTY, parameter = "goal", failureCode = 2),
            @Validate(validator = UserService.VALIDATOR_SIGN_IN, failureCode = 3)
    })
    public Object delete() {
        favoriteService.delete(request.get("goal"));

        return "";
    }
}
