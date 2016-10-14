package com.zoe.rus.geocoder;

import com.zoe.commons.ctrl.context.Request;
import com.zoe.commons.ctrl.execute.Execute;
import com.zoe.commons.ctrl.validate.Validate;
import com.zoe.commons.ctrl.validate.Validators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @author lpw
 */
@Controller(GeocoderModel.NAME + ".ctrl")
@Execute(name = "/geocoder/", key = GeocoderModel.NAME, code = "11")
public class GeocoderCtrl {
    @Autowired
    protected Request request;
    @Autowired
    protected GeocoderService geocoderService;

    /**
     * 获取GPS坐标对应的地址信息。
     *
     * @return {address:"",component:{}}。
     */
    @Execute(name = "address", validates = {
            @Validate(validator = Validators.MATCH_REGEX, parameter = "lat", string = {"^-?\\d{1,3}\\.\\d{3,6}$"}, failureCode = 1),
            @Validate(validator = Validators.MATCH_REGEX, parameter = "lng", string = {"^\\d{1,3}\\.\\d{3,6}$"}, failureCode = 2)
    })
    public Object address() {
        return geocoderService.address(request.get("lat"), request.get("lng"));
    }
}
