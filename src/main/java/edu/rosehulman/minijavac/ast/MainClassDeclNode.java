package edu.rosehulman.minijavac.ast;

import java.util.List;

public class MainClassDeclNode extends NonterminalNode {

    public MainClassDeclNode(List<StmtNode> sl) {
        children.addAll(sl);
    }

    @Override
    public String parseString() {
        return "MainClassDecl ::= ...";
    }
}
