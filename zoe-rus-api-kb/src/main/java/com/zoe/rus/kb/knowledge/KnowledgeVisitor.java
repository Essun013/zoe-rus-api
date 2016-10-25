package com.zoe.rus.kb.knowledge;

import org.commonmark.node.AbstractVisitor;
import org.commonmark.node.Image;
import org.commonmark.node.Text;

import java.util.Set;

/**
 * @author lpw
 */
class KnowledgeVisitor extends AbstractVisitor {
    static final String EMPTY = "KNOWLEDGE_VISITOR_EMPTY";
    static final String EMPTY_P = "<p>" + EMPTY + "</p>";

    private Set<String> kws;
    private StringBuilder mp;
    private StringBuilder sm;
    private StringBuilder lb;
    private String path;

    KnowledgeVisitor(Set<String> kws, StringBuilder mp, StringBuilder sm, StringBuilder lb, String path) {
        this.kws = kws;
        this.mp = mp;
        this.sm = sm;
        this.lb = lb;
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

        if (append(text, literal, "@MP ", mp))
            return;

        if (append(text, literal, "@SM ", sm))
            return;

        if (append(text, literal, "@LB ", lb))
            return;

        visitChildren(text);
    }

    protected boolean append(Text text, String literal, String tag, StringBuilder sb) {
        if (!literal.startsWith(tag))
            return false;

        sb.append(literal.substring(4));
        text.setLiteral(EMPTY);

        return true;
    }

    @Override
    public void visit(Image image) {
        image.setDestination(path + image.getDestination());
        visitChildren(image);
    }
}
