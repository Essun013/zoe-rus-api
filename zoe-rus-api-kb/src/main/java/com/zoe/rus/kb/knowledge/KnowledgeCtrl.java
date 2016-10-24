package com.zoe.rus.kb.knowledge;

import com.zoe.commons.ctrl.context.Request;
import com.zoe.commons.ctrl.execute.Execute;
import com.zoe.commons.ctrl.template.Templates;
import com.zoe.commons.ctrl.validate.Validate;
import com.zoe.commons.ctrl.validate.Validators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @author lpw
 */
@Controller(KnowledgeModel.NAME + ".ctrl")
@Execute(name = "/kb/knowledge/", key = KnowledgeModel.NAME, code = "21")
public class KnowledgeCtrl {
    @Autowired
    protected Request request;
    @Autowired
    protected Templates templates;
    @Autowired
    protected KnowledgeService knowledgeService;

    @Execute(name = "get", validates = {
            @Validate(validator = Validators.NOT_EMPTY, parameter = "id", failureCode = 1)
    })
    public Object get() {
        return knowledgeService.get(request.get("id"));
    }

    @Execute(name = "find", validates = {
            @Validate(validator = Validators.NOT_EMPTY, parameter = "subject", failureCode = 2)
    })
    public Object find() {
        return knowledgeService.find(request.get("subject"));
    }

    @Execute(name = "reload")
    public Object reload() {
        knowledgeService.reload();

        return "";
    }
}
