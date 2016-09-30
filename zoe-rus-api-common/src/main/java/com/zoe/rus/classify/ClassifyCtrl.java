package com.zoe.rus.classify;

import com.zoe.commons.ctrl.context.Request;
import com.zoe.commons.ctrl.execute.Execute;
import com.zoe.commons.ctrl.validate.Validate;
import com.zoe.commons.ctrl.validate.Validators;
import com.zoe.commons.dao.model.ModelHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * 分类管理。
 *
 * @auth lpw
 */
@Controller(ClassifyModel.NAME + ".ctrl")
@Execute(name = "/classify/", code = "10")
public class ClassifyCtrl {
    @Autowired
    protected ModelHelper modelHelper;
    @Autowired
    protected Request request;
    @Autowired
    protected ClassifyService classifyService;

    /**
     * 获取分类列表。
     * key 引用键。
     * parent 上级分类ID。
     *
     * @return [{key,parent,sort,name}]。
     */
    @Execute(name = "query", validates = {@Validate(validator = Validators.SIGN),
            @Validate(validator = Validators.NOT_EMPTY, parameters = {"key", "parent"}, failureCode = 1, failureArgKeys = {ClassifyModel.NAME + ".key-parent"})
    })
    public Object query() {
        return modelHelper.toJson(classifyService.query(request.get("key"), request.get("parent")));
    }

    /**
     * 保存分类信息。
     * id 分类ID值；不为空表示修改。
     * key 引用键；不为空表示新建第一级分类。
     * parent 上级分类；不为空表示新建非第一级分类。
     * sort 显示顺序。
     * name 分类名称。
     *
     * @return ""
     */
    @Execute(name = "save", validates = {@Validate(validator = Validators.SIGN),
            @Validate(validator = Validators.NOT_EMPTY, parameters = {"id", "key", "parent"}, failureCode = 11, failureArgKeys = {ClassifyModel.NAME + ".id-key-parent"}),
            @Validate(validator = Validators.NOT_EMPTY, parameter = "name", failureCode = 12, failureArgKeys = {ClassifyModel.NAME + ".name"}),
            @Validate(validator = Validators.MAX_LENGTH, parameter = "name", number = {100}, failureCode = 13, failureArgKeys = {ClassifyModel.NAME + ".name"})
    })
    public Object save() {
        classifyService.save(request.get("id"), request.get("key"), request.get("parent"), request.getAsInt("sort"), request.get("name"));

        return "";
    }
}
