package com.zoe.rus.milepost.physical;

import com.zoe.commons.ctrl.context.Request;
import com.zoe.commons.ctrl.execute.Execute;
import com.zoe.commons.ctrl.validate.Validate;
import com.zoe.commons.ctrl.validate.Validators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @author lpw
 */
@Controller(PhysicalModel.NAME + ".ctrl")
@Execute(name = "/milepost/physical/", key = PhysicalModel.NAME, code = "31")
public class PhysicalCtrl {
    @Autowired
    protected Request request;
    @Autowired
    protected PhysicalService physicalService;

    /**
     * 保存。
     * id 记录ID；为空则表示新增，否则为修改。
     * region 地区ID。
     * hospital 医院ID。医院ID与地区ID不允许同时为空。
     * type 类型：0-孕前；1-孕期；2-月子;3-婴幼儿。
     * sort 序号。
     * time 时间。
     * content 内容。
     * knowledge 对应知识。
     *
     * @return ""。
     */
    @Execute(name = "save", validates = {
            @Validate(validator = Validators.SIGN),
            @Validate(validator = Validators.NOT_EMPTY, parameters = {"region", "hospital"}, failureCode = 1, failureKey = PhysicalModel.NAME + ".region-hospital.empty"),
            @Validate(validator = Validators.BETWEEN, parameter = "type", number = {0, 9}, failureCode = 2),
            @Validate(validator = Validators.BETWEEN, parameter = "sort", number = {0, 99}, failureCode = 3),
            @Validate(validator = Validators.NOT_EMPTY, parameter = "time", failureCode = 4),
            @Validate(validator = Validators.MAX_LENGTH, parameter = "time", number = {100}, failureCode = 5),
            @Validate(validator = Validators.NOT_EMPTY, parameter = "content", failureCode = 6),
            @Validate(validator = Validators.NOT_EMPTY, parameter = "knowledge", failureCode = 7),
            @Validate(validator = Validators.MAX_LENGTH, parameter = "knowledge", number = {100}, failureCode = 8),
            @Validate(validator = PhysicalService.VALIDATOR_EXISTS, parameter = "id", failureCode = 9)
    })
    public Object save() {
        physicalService.save(request.setToModel(new PhysicalModel()));

        return "";
    }

    /**
     * 删除。
     * id 要删除的ID。
     *
     * @return ""。
     */
    @Execute(name = "delete", validates = {
            @Validate(validator = Validators.SIGN),
            @Validate(validator = Validators.NOT_EMPTY, parameter = "id", failureCode = 11),
            @Validate(validator = PhysicalService.VALIDATOR_EXISTS, parameter = "id", failureCode = 12)
    })
    public Object delete() {
        physicalService.delete(request.get("id"));

        return "";
    }
}
