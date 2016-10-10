package com.zoe.rus.uc.home;

import com.zoe.commons.ctrl.validate.ValidateWrapper;
import com.zoe.commons.ctrl.validate.ValidatorSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @auth lpw
 */
@Controller(HomeService.VALIDATOR_EXISTS_CODE)
public class ExistsCodeValidatorImpl extends ValidatorSupport {
    @Autowired
    protected HomeService homeService;

    @Override
    public boolean validate(ValidateWrapper validate, String parameter) {
        return homeService.findByCode(parameter) != null;
    }

    @Override
    protected String getDefaultFailureMessageKey() {
        return HomeModel.NAME + ".code.not-exists";
    }
}
