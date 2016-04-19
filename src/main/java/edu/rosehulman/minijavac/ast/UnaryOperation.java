package edu.rosehulman.minijavac.ast;

import edu.rosehulman.minijavac.typechecker.Scope;
import edu.rosehulman.minijavac.typechecker.Type;

import java.util.ArrayList;
import java.util.List;

public class UnaryOperation implements Expression {
    public final List<UnaryOperator> operators;
    public final CallExpression expression;

    @Override
    public List<String> typecheck(Scope scope) {
        List<String> errors = new ArrayList<>();
        if(!operators.isEmpty() && !operators.get(0).type.isA(expression.getType(scope), scope)) {
            errors.add("Operand type " + expression.getType(scope) + " is not compatible with expected type " + operators.get(0).type + " of operator " + operators.get(0));
        }
        errors.addAll(expression.typecheck(scope));
        return errors;
    }

    @Override
    public Type getType(Scope scope) {
        return expression.getType(scope);
    }

    public enum UnaryOperator {
        MINUS(Type.INT), NOT(Type.BOOLEAN);

        Type type;

        UnaryOperator(Type type) {
            this.type = type;
        }
    }

    public UnaryOperation(List<UnaryOperator> operators, CallExpression expression) {
        this.operators = operators;
        this.expression = expression;
    }
}
