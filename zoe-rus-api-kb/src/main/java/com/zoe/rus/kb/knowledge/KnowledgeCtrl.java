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
@Execute(name = "/kb/knowledge/",key = KnowledgeModel.NAME, code = "21")
public class KnowledgeCtrl {
    @Autowired
    protected Request request;
    @Autowired
    protected Templates templates;
    @Autowired
    protected KnowledgeService knowledgeService;

    @Execute(name = "get", type = Templates.STRING)
    public Object get() {
        templates.get(Templates.STRING).setContentType("text/html");
        String html = knowledgeService.get(request.get("id"));

        return html == null ? "" : html;
    }

    @Execute(name = "reload", validates = {@Validate(validator = Validators.SIGN)})
    public Object reload() {
        knowledgeService.reload();

        return "";
    }
}
