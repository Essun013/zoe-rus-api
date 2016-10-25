package com.zoe.rus.kb.knowledge;

import com.zoe.commons.dao.model.Jsonable;
import com.zoe.rus.model.ModelSupport;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author lpw
 */
@Component(KnowledgeModel.NAME + ".model")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Entity(name = KnowledgeModel.NAME)
@Table(name = "t_kb_knowledge")
public class KnowledgeModel extends ModelSupport {
    static final String NAME = "rus.kb.knowledge";

    private String classify; // 分类
    private int sort; // 顺序
    private String subject; // 标题
    private int start; // 开始天数
    private int end; // 结束天数
    private String image; // 主图
    private String thumbnail; // 缩略图
    private String summary; // 摘要
    private String label; // 标签
    private String content; // 内容
    private String html; // HTML内容

    @Jsonable
    @Column(name = "c_classify")
    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }

    @Jsonable
    @Column(name = "c_sort")
    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    @Jsonable
    @Column(name = "c_subject")
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Jsonable
    @Column(name = "c_start")
    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    @Jsonable
    @Column(name = "c_end")
    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    @Jsonable
    @Column(name = "c_image")
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Jsonable
    @Column(name = "c_thumbnail")
    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    @Jsonable
    @Column(name = "c_summary")
    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @Jsonable
    @Column(name = "c_label")
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Jsonable
    @Column(name = "c_content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Jsonable
    @Column(name = "c_html")
    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }
}
