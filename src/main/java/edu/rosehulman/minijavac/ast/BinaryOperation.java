package edu.rosehulman.minijavac.ast;

import com.google.common.collect.ImmutableList;
import edu.rosehulman.minijavac.generator.ConstantPool;
import edu.rosehulman.minijavac.generator.Variable;
import edu.rosehulman.minijavac.typechecker.Scope;
import edu.rosehulman.minijavac.typechecker.Type;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static edu.rosehulman.minijavac.ast.BinaryOperation.BinaryOperator.EQ;
import static edu.rosehulman.minijavac.ast.BinaryOperation.BinaryOperator.GT;
import static edu.rosehulman.minijavac.ast.BinaryOperation.BinaryOperator.GTE;
import static edu.rosehulman.minijavac.ast.BinaryOperation.BinaryOperator.LT;
import static edu.rosehulman.minijavac.ast.BinaryOperation.BinaryOperator.LTE;
import static edu.rosehulman.minijavac.ast.BinaryOperation.BinaryOperator.NEQ;

public class BinaryOperation implements Expression {
    public final BinaryOperator operator;
    public final Expression left;
    public final Expression right;
    List<Byte> operatorBytes;

    @Override
    public List<String> typecheck(Scope scope) {
        List<String> errors = new ArrayList<>();
        operatorBytes = new ArrayList<>();
        operatorBytes.addAll(operator.byteCode);
        if (operator == BinaryOperator.EQ || operator == BinaryOperator.NEQ) {
            errors.addAll(left.typecheck(scope));
            errors.addAll(right.typecheck(scope));
            Type leftType = left.getType(scope);
            Type rightType = right.getType(scope);
            if (leftType.isPrimitiveType() != rightType.isPrimitiveType()) {
                errors.add("The operand types, " + leftType + " and " + rightType + ", are not compatible for equality comparison");
            }
            if (!leftType.isPrimitiveType()) {
                // if_acmpeq instead of if_icmpeq
                operatorBytes.set(0, (byte) (operatorBytes.get(0) + 6));
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
        if (operator == BinaryOperator.AND) {
            bytes.add((byte) 89);   // dup
            bytes.add((byte) 153);  // ifeq means top of stack is zero
            int jumpLength = rightBytes.size() + operatorBytes.size() + 3;
            bytes.add((byte) (jumpLength >> 8));
            bytes.add((byte) jumpLength);
            bytes.addAll(rightBytes);
            bytes.addAll(operatorBytes);
        } else if (operator == BinaryOperator.OR) {
            bytes.add((byte) 89);   // dup
            bytes.add((byte) 154);  // ifne means top of stack is not zero
            int jumpLength = rightBytes.size() + operatorBytes.size() + 3;
            bytes.add((byte) (jumpLength >> 8));
            bytes.add((byte) jumpLength);
            bytes.addAll(rightBytes);
            bytes.addAll(operatorBytes);
        } else {
            bytes.addAll(rightBytes);
            bytes.addAll(operatorBytes);
        }
        return bytes;
    }

    @Override
    public int maxBlockDepth() {
        BinaryOperator o = operator;
        if(o == LT || o == LTE || o == GT || o == GTE || o == EQ || o == NEQ) {
            return 1;
        } else {
            return 0;
        }
    }

    public enum BinaryOperator {
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
    }

    public BinaryOperation(BinaryOperator operator, Expression left, Expression right) {
        this.operator = operator;
        this.left = left;
        this.right = right;
    }
}
