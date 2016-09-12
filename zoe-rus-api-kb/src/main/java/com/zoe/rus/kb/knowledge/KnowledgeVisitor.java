package com.zoe.rus.kb.knowledge;

import org.commonmark.node.AbstractVisitor;
import org.commonmark.node.Image;

/**
 * @author lpw
 */
class KnowledgeVisitor extends AbstractVisitor {
    private String path;

    KnowledgeVisitor(String path) {
        this.path = path;
    }

    @Override
    public void visit(Image image) {
        if (image.getDestination().startsWith("img/"))
            image.setDestination(KnowledgeService.PATH + path + "/" + image.getDestination());

        visitChildren(image);
    }
}
