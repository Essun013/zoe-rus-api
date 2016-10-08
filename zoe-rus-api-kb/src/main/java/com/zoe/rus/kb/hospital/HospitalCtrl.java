package com.zoe.rus.kb.hospital;

import com.zoe.commons.ctrl.context.Request;
import com.zoe.commons.ctrl.execute.Execute;
import com.zoe.commons.ctrl.validate.Validate;
import com.zoe.commons.ctrl.validate.Validators;
import com.zoe.commons.dao.model.ModelHelper;
import com.zoe.rus.classify.ClassifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * 医院信息。
 *
 * @author lpw
 */
@Controller(HospitalModel.NAME + ".ctrl")
@Execute(name = "/kb/hospital/", key = HospitalModel.NAME, code = "20")
public class HospitalCtrl {
    @Autowired
    protected ModelHelper modelHelper;
    @Autowired
    protected Request request;
    @Autowired
    protected HospitalService hospitalService;

    /**
     * 检索医院信息。
     * region 地区ID值。
     *
     * @return [{region,name,longitude,latitude}]
     */
    @Execute(name = "query")
    public Object query() {
        return modelHelper.toJson(hospitalService.query(request.get("region")));
    }

    /**
     * 保存医院信息。
     * id ID，如果不存在则新增，否则修改。
     * region 区域ID。
     * name 名称。
     * longitude 经度。
     * latitude 纬度。
     *
     * @return ""。
     */
    @Execute(name = "save", validates = {@Validate(validator = Validators.SIGN),
            @Validate(validator = Validators.NOT_EMPTY, parameter = "region", failureCode = 1),
            @Validate(validator = Validators.NOT_EMPTY, parameter = "name", failureCode = 2),
            @Validate(validator = Validators.MAX_LENGTH, number = {100}, parameter = "name", failureCode = 3),
            @Validate(validator = Validators.MATCH_REGEX, string = {"^-?\\d{1,3}\\.\\d+$"}, parameter = "longitude", failureCode = 4),
            @Validate(validator = Validators.MATCH_REGEX, string = {"^-?\\d{1,3}\\.\\d+$"}, parameter = "latitude", failureCode = 5),
            @Validate(validator = ClassifyService.EXISTS_VALIDATOR, parameter = "region", failureCode = 6)
    })
    public Object save() {
        hospitalService.save(request.setToModel(new HospitalModel()));

        return "";
    }
}
