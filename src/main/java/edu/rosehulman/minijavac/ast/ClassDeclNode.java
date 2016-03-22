package edu.rosehulman.minijavac.ast;

import java.util.List;

public class ClassDeclNode extends NonterminalNode {
    public ClassDeclNode(List<ClassVarDeclNode> cvdl, List<MethodDeclNode> mdl) {
        children.addAll(cvdl);
        children.addAll(mdl);
    }

    @Override
    public String parseString() {
        return "ClassDecl ::= ...";
    }
}
