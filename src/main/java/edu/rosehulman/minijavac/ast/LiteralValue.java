package edu.rosehulman.minijavac.ast;

public class LiteralValue implements LiteralExpression {
    public final String type;
    public final Object value;

    public LiteralValue(String type, Object value) {
        this.type = type;
        this.value = value;
    }
}
