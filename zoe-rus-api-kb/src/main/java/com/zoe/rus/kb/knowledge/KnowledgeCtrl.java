package com.zoe.rus.kb.knowledge;

import com.zoe.commons.ctrl.context.Request;
import com.zoe.commons.ctrl.execute.Execute;
import com.zoe.commons.ctrl.template.Templates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @author lpw
 */
@Controller(KnowledgeModel.NAME + ".ctrl")
@Execute(name = "/kb/knowledge/")
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

    @Execute(name = "reload")
    public Object reload() {
        knowledgeService.reload();

        return "";
    }
}
