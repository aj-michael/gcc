package edu.rosehulman.minijavac.ast;

import java.util.List;

public class UnaryOperation {
    public final List<UnaryOperator> operators;
    public final CallExpression expression;

    public enum UnaryOperator {
        MINUS, NOT
    }

    public UnaryOperation(List<UnaryOperator> operators, CallExpression expression) {
        this.operators = operators;
        this.expression = expression;
    }
}
