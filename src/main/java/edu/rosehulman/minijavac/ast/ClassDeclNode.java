package edu.rosehulman.minijavac.ast;

import java.util.List;

public class ClassDeclNode extends NonterminalNode {
    public ClassDeclNode(List<ClassVarDeclNode> cvdl, List<MethodDeclNode> mdl) {
        if (cvdl != null) children.addAll(cvdl);
        if (mdl != null) children.addAll(mdl);
    }

    @Override
    public String parseString() {
        return "ClassDecl ::= ...";
    }
}
