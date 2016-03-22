package edu.rosehulman.minijavac.ast;

import java.util.ArrayList;
import java.util.List;

public class TerminalNode extends Node {

    @Override
    public List<Node> getChildren() {
        return new ArrayList<>();
    }

    @Override
    public String parseString() {
        return "Terminal";
    }
}
