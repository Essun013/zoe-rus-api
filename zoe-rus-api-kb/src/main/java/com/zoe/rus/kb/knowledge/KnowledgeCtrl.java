package com.zoe.rus.kb.knowledge;

import com.zoe.commons.ctrl.context.Request;
import com.zoe.commons.ctrl.execute.Execute;
import com.zoe.commons.ctrl.template.Templates;
import com.zoe.commons.ctrl.validate.Validate;
import com.zoe.commons.ctrl.validate.Validators;
import net.sf.json.JSONObject;
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

    /**
     * 获取知识。
     * id 知识ID值。
     * html 是否包含html数据，true为包含，其他为不包含。
     *
     * @return {KnowledgeModel}。
     */
    @Execute(name = "get", validates = {
            @Validate(validator = Validators.NOT_EMPTY, parameter = "id", failureCode = 1)
    })
    public Object get() {
        JSONObject knowledge = knowledgeService.get(request.get("id"), hasHtml());

        return knowledge == null ? new JSONObject() : knowledge;
    }

    /**
     * 获取知识。
     * subject 知识标题。
     * html 是否包含html数据，true为包含，其他为不包含。
     *
     * @return {KnowledgeModel}。
     */
    @Execute(name = "find", validates = {
            @Validate(validator = Validators.NOT_EMPTY, parameter = "subject", failureCode = 2)
    })
    public Object find() {
        JSONObject knowledge = knowledgeService.find(request.get("subject"), hasHtml());

        return knowledge == null ? new JSONObject() : knowledge;
    }

    protected boolean hasHtml() {
        return "true".equals(request.get("html"));
    }

    /**
     * 获取HTML内容。
     * id 知识ID值。
     *
     * @return ""。
     */
    @Execute(name = "html", type = Templates.STRING, validates = {
            @Validate(validator = Validators.NOT_EMPTY, parameter = "id", failureCode = 1)
    })
    public Object html() {
        templates.get(Templates.STRING).setContentType("text/html");
        String html = knowledgeService.getHtml(request.get("id"));

        return html == null ? "" : html;
    }

    @Execute(name = "reload")
    public Object reload() {
        knowledgeService.reload();

        return "";
    }
}
