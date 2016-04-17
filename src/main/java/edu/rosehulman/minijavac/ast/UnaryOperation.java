package edu.rosehulman.minijavac.ast;

import edu.rosehulman.minijavac.typechecker.Scope;

import java.util.ArrayList;
import java.util.List;

public class UnaryOperation implements Expression {
    public final List<UnaryOperator> operators;
    public final CallExpression expression;

    @Override
    public List<String> typecheck(Scope scope) {
        List<String> errors = new ArrayList<>();
        errors.addAll(expression.typecheck(scope));
        return errors;
    }

    @Override
    public String getType(Scope scope) {
        return expression.getType(scope);
    }

    public enum UnaryOperator {
        MINUS, NOT
    }

    public UnaryOperation(List<UnaryOperator> operators, CallExpression expression) {
        this.operators = operators;
        this.expression = expression;
    }
}
