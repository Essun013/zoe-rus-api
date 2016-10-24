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
    private StringBuilder sm;
    private String path;

    KnowledgeVisitor(Set<String> kws, List<String> mps, StringBuilder sm, String path) {
        this.kws = kws;
        this.mps = mps;
        this.sm = sm;
        this.path = path;
    }

    @Override
    public void visit(Text text) {
        String literal = text.getLiteral().trim();
        if (literal.startsWith("@KW ")) {
            for (String kw : literal.split(" "))
                kws.add(kw);
            text.setLiteral(EMPTY);

            return;
        }

        if (literal.startsWith("@MP ")) {
            for (String mp : literal.split("-"))
                mps.add(mp);
            text.setLiteral(EMPTY);

            return;
        }

        if (literal.startsWith("@SM ")) {
            sm.append(literal.substring(4));
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
