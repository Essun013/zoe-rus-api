package com.zoe.rus.kb.keyword;

import com.zoe.commons.ctrl.context.Request;
import com.zoe.commons.ctrl.execute.Execute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * 关键词。
 *
 * @author lpw
 */
@Controller(KeyWordModel.NAME + ".ctrl")
@Execute(name = "/kb/key-word/", code = "22")
public class KeyWordCtrl {
    @Autowired
    protected Request request;
    @Autowired
    protected KeyWordService keyWordService;

    /**
     * 获取关键词。
     *
     * @return []。
     */
    @Execute(name = "words")
    public Object words() {
        return keyWordService.words();
    }
}
