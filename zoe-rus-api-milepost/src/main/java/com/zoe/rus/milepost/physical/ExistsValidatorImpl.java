package com.zoe.rus.milepost.physical;

import com.zoe.commons.ctrl.validate.ValidateWrapper;
import com.zoe.commons.ctrl.validate.ValidatorSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @auth lpw
 */
@Controller(PhysicalService.VALIDATOR_EXISTS)
public class ExistsValidatorImpl extends ValidatorSupport {
    @Autowired
    protected PhysicalService physicalService;

    @Override
    public boolean validate(ValidateWrapper validate, String parameter) {
        return validator.isEmpty(parameter) || physicalService.findById(parameter) != null;
    }

    @Override
    protected String getDefaultFailureMessageKey() {
        return PhysicalModel.NAME + ".not-exists";
    }
}
