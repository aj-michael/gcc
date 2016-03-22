package edu.rosehulman.minijavac.ast;

import java.util.List;

public class ProgramNode extends NonterminalNode {
    final MainClassDeclNode mcl;
    final List<ClassDeclNode> cdl;

    public ProgramNode(MainClassDeclNode mcl, List<ClassDeclNode> cdl) {
        this.mcl = mcl;
        this.cdl = cdl;
        children.add(mcl);
        children.addAll(cdl);
    }

    @Override
    public String parseString() {
        return "Program ::= MainClassDecl ClassDeclList";
    }
}
