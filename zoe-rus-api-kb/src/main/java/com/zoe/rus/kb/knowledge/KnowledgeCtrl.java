package com.zoe.rus.kb.knowledge;

import com.zoe.commons.ctrl.context.Request;
import com.zoe.commons.ctrl.context.Response;
import com.zoe.commons.ctrl.execute.Execute;
import com.zoe.commons.ctrl.template.Templates;
import com.zoe.commons.ctrl.validate.Validate;
import com.zoe.commons.ctrl.validate.Validators;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lpw
 */
@Controller(KnowledgeModel.NAME + ".ctrl")
@Execute(name = "/kb/knowledge/", key = KnowledgeModel.NAME, code = "21")
public class KnowledgeCtrl {
    @Autowired
    protected Request request;
    @Autowired
    protected Response response;
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
        JSONObject knowledge = knowledgeService.get(request.get("id"));

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
        JSONObject knowledge = knowledgeService.find(request.get("subject"));

        return knowledge == null ? new JSONObject() : knowledge;
    }

    /**
     * 获取HTML内容。
     * id 知识ID值。
     * css CSS文件名，不包含后缀，同时引用多个CSS以逗号分割。
     *
     * @return ""。
     */
    @Execute(name = "html", type = Templates.FREEMARKER, template = "html", validates = {
            @Validate(validator = Validators.NOT_EMPTY, parameter = "id", failureCode = 1)
    })
    public Object html() {
        Map<String, Object> map = new HashMap<>();
        String[] css = request.getAsArray("css");
        map.put("css", css.length == 0 ? new String[]{"md"} : css);
        map.put("html", knowledgeService.getHtml(request.get("id")));

        return map;
    }

    /**
     * 检索知识。
     * classify 分类，多个级间以逗号分割。
     * day 天数，即@PM设置的时间。
     * image 仅获取有大图的知识，true/false。
     * pageSize 每页显示记录数。
     * pageNum 当前显示页数。
     *
     * @return {count,page,number,list:[]}。
     */
    @Execute(name = "query")
    public Object query() {
        return knowledgeService.query(request.getAsArray("classify"), request.getAsInt("day"), "true".equals(request.get("image")));
    }

    /**
     * 搜索知识。
     * kw 关键词。
     * pageSize 每页显示记录数。
     * pageNum 当前显示页数。
     *
     * @return {count,page,number,list:[]}。
     */
    @Execute(name = "search", validates = {
            @Validate(validator = Validators.NOT_EMPTY, parameter = "kw", failureCode = 3)
    })
    public Object search() {
        return knowledgeService.search(request.get("kw"));
    }

    /**
     * 重新生成知识。
     *
     * @return ""。
     */
    @Execute(name = "reload", validates = {
            @Validate(validator = Validators.SIGN)
    })
    public Object reload() {
        knowledgeService.reload();

        return "";
    }
}
