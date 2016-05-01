package edu.rosehulman.minijavac.ast;

import edu.rosehulman.minijavac.generator.ConstantPool;
import edu.rosehulman.minijavac.generator.Variable;
import edu.rosehulman.minijavac.typechecker.Scope;
import edu.rosehulman.minijavac.typechecker.Type;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UnaryOperation implements Expression {
    public final List<UnaryOperator> operators;
    public final CallExpression expression;

    @Override
    public List<String> typecheck(Scope scope) {
        List<String> errors = new ArrayList<>();
        if (!operators.isEmpty() && !operators.get(0).type.isA(expression.getType(scope), scope)) {
            errors.add("Operand type " + expression.getType(scope) + " is not compatible with expected type " + operators.get(0).type + " of operator " + operators.get(0));
        }
        errors.addAll(expression.typecheck(scope));
        return errors;
    }

    @Override
    public Type getType(Scope scope) {
        if (operators.isEmpty()) {
            return expression.getType(scope);
        } else {
            return operators.get(0).type;
        }
    }

    @Override
    public void addConstantPoolEntries(ConstantPool cp) {
        expression.addConstantPoolEntries(cp);
    }

    @Override
    public int maxBlockDepth() {
        return expression.maxBlockDepth();
    }

    @Override
    public List<Byte> generateCode(ConstantPool cp, Map<String, Variable> variables) {
        if(operators.size() % 2 == 0) {
            return expression.generateCode(cp, variables);
        } else if(operators.get(0).equals(UnaryOperator.MINUS)) {
            ArrayList<Byte> bytes = new ArrayList<>();
            bytes.addAll(expression.generateCode(cp, variables));
            bytes.add((byte) 116); // ineg
            return bytes;
        } else {
            ArrayList<Byte> bytes = new ArrayList<>();
            bytes.addAll(expression.generateCode(cp, variables));
            bytes.add((byte) 154); // ifne
            bytes.add((byte) 0);
            bytes.add((byte) 7);
            bytes.add((byte) 4); // iconst_1
            bytes.add((byte) 167); // goto
            bytes.add((byte) 0);
            bytes.add((byte) 4);
            bytes.add((byte) 3); // iconst_0
            return bytes;
        }
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
