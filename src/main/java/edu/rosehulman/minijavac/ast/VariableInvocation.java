package edu.rosehulman.minijavac.ast;

public class VariableInvocation implements LiteralExpression {
    public final String name;

    public VariableInvocation(String name) {
        this.name = name;
    }
}
