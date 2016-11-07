package com.zoe.rus.kb.keyword;

import com.zoe.commons.dao.model.Jsonable;
import com.zoe.commons.dao.model.TephraModelSupport;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author lpw
 */
@Component(KeyWordModel.NAME + ".model")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Entity(name = KeyWordModel.NAME)
@Table(name = "t_kb_key_word")
public class KeyWordModel extends TephraModelSupport {
    static final String NAME = "rus.kb.key-word";

    private String word;
    private String knowledge;

    @Jsonable
    @Column(name = "c_word")
    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    @Jsonable
    @Column(name = "c_knowledge")
    public String getKnowledge() {
        return knowledge;
    }

    public void setKnowledge(String knowledge) {
        this.knowledge = knowledge;
    }
}
