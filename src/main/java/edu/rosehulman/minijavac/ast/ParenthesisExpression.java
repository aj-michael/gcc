package edu.rosehulman.minijavac.ast;

public class ParenthesisExpression implements LiteralExpression {
    public final Expression expression;

    public ParenthesisExpression(Expression expression) {
        this.expression = expression;
    }
}
