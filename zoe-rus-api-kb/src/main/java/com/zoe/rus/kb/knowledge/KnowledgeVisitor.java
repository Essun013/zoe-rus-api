package com.zoe.rus.kb.knowledge;

import org.commonmark.node.AbstractVisitor;
import org.commonmark.node.Image;
import org.commonmark.node.Text;

import java.util.List;
import java.util.Set;

/**
 * @author lpw
 */
class KnowledgeVisitor extends AbstractVisitor {
    static final String EMPTY = "KNOWLEDGE_VISITOR_EMPTY";
    static final String EMPTY_P = "<p>" + EMPTY + "</p>";

    private Set<String> kws;
    private List<String> mps;
    private String path;

    KnowledgeVisitor(Set<String> kws, List<String> mps, String path) {
        this.kws = kws;
        this.mps = mps;
        this.path = path;
    }

    @Override
    public void visit(Text text) {
        String literal = text.getLiteral().trim();
        if (literal.startsWith("@KW ")) {
            String[] array = literal.split(" ");
            for (int i = 1; i < array.length; i++)
                kws.add(array[i]);
            text.setLiteral(EMPTY);

            return;
        }
        if (literal.startsWith("@MP ")) {
            text.setLiteral(EMPTY);

            return;
        }

        visitChildren(text);
    }

    @Override
    public void visit(Image image) {
        image.setDestination(KnowledgeService.PATH + path + image.getDestination());
        visitChildren(image);
    }
}
