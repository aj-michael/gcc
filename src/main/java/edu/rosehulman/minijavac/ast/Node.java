package edu.rosehulman.minijavac.ast;

import java.util.List;

public abstract class Node {

    public abstract List<Node> getChildren();
    public abstract String parseString();

    public String postorderTraversal() {
        StringBuffer buffer = new StringBuffer();
        for (Node child : getChildren()) {
            if (child != null) {
                buffer.append(child.postorderTraversal()).append("\n");
            }
        }
        return buffer.append(parseString()).toString();
    }
}
