package edu.rosehulman.minijavac.ast;

import com.google.common.collect.ImmutableList;
import edu.rosehulman.minijavac.generator.ConstantPool;
import edu.rosehulman.minijavac.generator.Variable;
import edu.rosehulman.minijavac.operators.*;
import edu.rosehulman.minijavac.typechecker.Scope;
import edu.rosehulman.minijavac.typechecker.Type;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BinaryOperation implements Expression {
    public final Operator operator;
    public final Expression left;
    public final Expression right;
    private Type type;

    public BinaryOperation(Operator operator, Expression left, Expression right) {
        this.operator = operator;
        this.left = left;
        this.right = right;
    }

    @Override
    public List<String> typecheck(Scope scope) {
        List<String> errors = new ArrayList<>();


        if (operator instanceof EqualsEquals || operator instanceof NotEquals) {
            errors.addAll(left.typecheck(scope));
            errors.addAll(right.typecheck(scope));
            Type leftType = left.getType(scope);
            Type rightType = right.getType(scope);
            if (leftType.isPrimitiveType() != rightType.isPrimitiveType()) {
                errors.add("The operand types, " + leftType + " and " + rightType + ", are not compatible for equality comparison");
            }
        } else {
            if (!operator.isOperationSupported(left.getType(scope))) {
                errors.add("Left argument of type " + left.getType(scope) + " does not match expected type for operator " + operator);
            }
            errors.addAll(left.typecheck(scope));
            if (!operator.isOperationSupported(right.getType(scope))) {
                errors.add("Right argument of type " + right.getType(scope) + " does not match expected type for operator " + operator);
            }

            errors.addAll(right.typecheck(scope));
        }
        Type leftType = left.getType(scope);
        Type rightType = right.getType(scope);
        type = leftType;
        if(leftType.isPrimitiveType() && rightType.isPrimitiveType() && leftType != rightType) {
            errors.add("The operand type, " + leftType + " and " + rightType + ", are not compatible");
        }
        return errors;
    }

    @Override
    public Type getType(Scope scope) {
        return operator.returnType(left.getType(scope));
    }

    @Override
    public void addConstantPoolEntries(ConstantPool cp) {
        left.addConstantPoolEntries(cp);
        right.addConstantPoolEntries(cp);
    }

    @Override
    public List<Byte> generateCode(ConstantPool cp, Map<String, Variable> variables) {
        ArrayList<Byte> bytes = new ArrayList<>();
        bytes.addAll(left.generateCode(cp, variables));
        List<Byte> rightBytes = right.generateCode(cp, variables);
        if (operator instanceof And) {
            bytes.add((byte) 89);   // dup
            bytes.add((byte) 153);  // ifeq means top of stack is zero
            int jumpLength = rightBytes.size() + operator.getOperatorBytes(type).size() + 3;
            bytes.add((byte) (jumpLength >> 8));
            bytes.add((byte) jumpLength);
            bytes.addAll(rightBytes);
            bytes.addAll(operator.getOperatorBytes(type));
        } else if (operator instanceof Or) {
            bytes.add((byte) 89);   // dup
            bytes.add((byte) 154);  // ifne means top of stack is not zero
            int jumpLength = rightBytes.size() + operator.getOperatorBytes(type).size() + 3;
            bytes.add((byte) (jumpLength >> 8));
            bytes.add((byte) jumpLength);
            bytes.addAll(rightBytes);
            bytes.addAll(operator.getOperatorBytes(type));
        } else {
            bytes.addAll(rightBytes);
            bytes.addAll(operator.getOperatorBytes(type));
        }
        return bytes;
    }

    @Override
    public int maxBlockDepth() {
        /*BinaryOperator o = operator;
        if(o == LT || o == LTE || o == GT || o == GTE || o == EQ || o == NEQ) {
            return 1;
        } else {
            return 0;
        }*/
        return 0;
    }

    /*public enum BinaryOperator {
        PLUS(Type.INT, Type.INT, ImmutableList.of((byte) 96)),
        MINUS(Type.INT, Type.INT, ImmutableList.of((byte) 100)),
        MULTIPLY(Type.INT, Type.INT, ImmutableList.of((byte) 104)),
        DIVIDE(Type.INT, Type.INT, ImmutableList.of((byte) 108)),
        LT(Type.INT, Type.BOOLEAN, ImmutableList.of((byte) 162, (byte) 0, (byte) 7, (byte) 4, (byte) 167, (byte) 0, (byte) 4, (byte) 3)),
        LTE(Type.INT, Type.BOOLEAN, ImmutableList.of((byte) 163, (byte) 0, (byte) 7, (byte) 4, (byte) 167, (byte) 0, (byte) 4, (byte) 3)),
        GT(Type.INT, Type.BOOLEAN, ImmutableList.of((byte) 164, (byte) 0, (byte) 7, (byte) 4, (byte) 167, (byte) 0, (byte) 4, (byte) 3)),
        GTE(Type.INT, Type.BOOLEAN, ImmutableList.of((byte) 161, (byte) 0, (byte) 7, (byte) 4, (byte) 167, (byte) 0, (byte) 4, (byte) 3)),
        AND(Type.BOOLEAN, Type.BOOLEAN, ImmutableList.of((byte) 126)),
        OR(Type.BOOLEAN, Type.BOOLEAN, ImmutableList.of((byte) 128)),
        EQ(null, Type.BOOLEAN, ImmutableList.of((byte) 160, (byte) 0, (byte) 7, (byte) 4, (byte) 167, (byte) 0, (byte) 4, (byte) 3)),
        NEQ(null, Type.BOOLEAN, ImmutableList.of((byte) 159, (byte) 0, (byte) 7, (byte) 4, (byte) 167, (byte) 0, (byte) 4, (byte) 3));

        Type operandType;
        Type returnType;
        List<Byte> byteCode;

        BinaryOperator(Type operandType, Type returnType, List<Byte> byteCode) {
            this.operandType = operandType;
            this.returnType = returnType;
            this.byteCode = byteCode;
        }
    }*/
}
