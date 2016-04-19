package edu.rosehulman.minijavac.ast;

import java.util.ArrayList;
import java.util.List;

import edu.rosehulman.minijavac.typechecker.Scope;
import edu.rosehulman.minijavac.typechecker.Type;

public class BinaryOperation implements Expression {
    public final BinaryOperator operator;
    public final Expression left;
    public final Expression right;

    @Override
    public List<String> typecheck(Scope scope) {
        List<String> errors = new ArrayList<>();
        if (operator == BinaryOperator.EQ || operator == BinaryOperator.NEQ) {
            errors.addAll(left.typecheck(scope));
            errors.addAll(right.typecheck(scope));
            Type leftType = left.getType(scope);
            Type rightType = right.getType(scope);
            if (leftType.isPrimitiveType() ^ rightType.isPrimitiveType()) {
                errors.add("The operand types, " + leftType + " and " + rightType + ", are not compatible for equality comparison");
            }
        } else {
            if (!left.getType(scope).isA(operator.operandType, scope)) {
                errors.add("Left argument of type " + left.getType(scope) + " does not match expected type " +
                        operator.operandType + " for operator " + operator.name());
            }
            errors.addAll(left.typecheck(scope));
            if (!right.getType(scope).isA(operator.operandType, scope)) {
                errors.add("Right argument of type " + right.getType(scope) + " does not match expected type " +
                        operator.operandType + " for operator " + operator.name());
            }
            errors.addAll(right.typecheck(scope));
        }
        return errors;
    }

    @Override
    public Type getType(Scope scope) {
        return operator.returnType;
    }

    public enum BinaryOperator {
        PLUS(Type.INT, Type.INT),
        MINUS(Type.INT, Type.INT),
        MULTIPLY(Type.INT, Type.INT),
        DIVIDE(Type.INT, Type.INT),
        LT(Type.INT, Type.BOOLEAN),
        LTE(Type.INT, Type.BOOLEAN),
        GT(Type.INT, Type.BOOLEAN),
        GTE(Type.INT, Type.BOOLEAN),
        AND(Type.BOOLEAN, Type.BOOLEAN),
        OR(Type.BOOLEAN, Type.BOOLEAN),
        EQ(null, Type.BOOLEAN),
        NEQ(null, Type.BOOLEAN);

        Type operandType;
        Type returnType;

        BinaryOperator(Type operandType, Type returnType) {
            this.operandType = operandType;
            this.returnType = returnType;
        }
    }

    public BinaryOperation(BinaryOperator operator, Expression left, Expression right) {
        this.operator = operator;
        this.left = left;
        this.right = right;
    }
}
