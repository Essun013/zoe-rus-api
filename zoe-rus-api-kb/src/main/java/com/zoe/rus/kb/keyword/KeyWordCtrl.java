package com.zoe.rus.kb.keyword;

import com.zoe.commons.ctrl.context.Request;
import com.zoe.commons.ctrl.execute.Execute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @author lpw
 */
@Controller(KeyWordModel.NAME + ".ctrl")
@Execute(name = "/kb/key-word/")
public class KeyWordCtrl {
    @Autowired
    protected Request request;
    @Autowired
    protected KeyWordService keyWordService;

    @Execute(name = "words")
    public Object words() {
        return keyWordService.words();
    }
}
