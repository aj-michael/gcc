package edu.rosehulman.minijavac.ast;

public class BinaryOperation implements Expression {
    public final BinaryOperator operator;
    public final Expression left;
    public final Expression right;

    public enum BinaryOperator {
        PLUS, MINUS, MULTIPLY, DIVIDE, LT, LTE, GT, GTE, EQ, NEQ, AND, OR
    }

    public BinaryOperation(BinaryOperator operator, Expression left, Expression right) {
        this.operator = operator;
        this.left = left;
        this.right = right;
    }
}
