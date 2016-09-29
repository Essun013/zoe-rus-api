package com.zoe.rus.classify;

import com.zoe.commons.ctrl.validate.ValidateWrapper;
import com.zoe.commons.ctrl.validate.ValidatorSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @auth lpw
 */
@Controller(ClassifyService.EXISTS_VALIDATOR)
public class ExistsValidatorImpl extends ValidatorSupport {
    @Autowired
    protected ClassifyService classifyService;

    @Override
    public boolean validate(ValidateWrapper validate, String parameter) {
        return classifyService.findById(parameter) != null;
    }

    @Override
    protected String getDefaultFailureMessageKey() {
        return ClassifyModel.NAME + ".not-exists";
    }
}
