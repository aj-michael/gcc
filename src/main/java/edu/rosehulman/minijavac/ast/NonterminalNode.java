package edu.rosehulman.minijavac.ast;

import java.util.ArrayList;
import java.util.List;

public abstract class NonterminalNode extends Node {
    List<Node> children = new ArrayList<>();

    @Override
    public List<Node> getChildren() {
        return children;
    }

}
