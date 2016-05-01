package edu.rosehulman.minijavac.ast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.rosehulman.minijavac.generator.ConstantPool;
import edu.rosehulman.minijavac.generator.Variable;
import edu.rosehulman.minijavac.typechecker.Scope;
import edu.rosehulman.minijavac.typechecker.Type;

public class IfStatement implements Statement {
    public final Expression condition;
    public final Statement trueStatement;
    public final Statement falseStatement;

    public IfStatement(Expression condition, Statement trueStatement, Statement falseStatement) {
        this.condition = condition;
        this.trueStatement = trueStatement;
        this.falseStatement = falseStatement;
    }

    @Override
    public List<String> typecheck(Scope scope) {
        List<String> errors = new ArrayList<>();

        Type conditionType = condition.getType(scope);
        if (!conditionType.isA(Type.BOOLEAN, scope)) {
            errors.add("Condition for if statement is of type " + conditionType + " instead of boolean");
        }

        errors.addAll(condition.typecheck(scope));
        errors.addAll(trueStatement.typecheck(scope));
        errors.addAll(falseStatement.typecheck(scope));
        return errors;
    }

    @Override
    public int numLocalVariables(List<Variable> vd) {
        return trueStatement.numLocalVariables(vd) + falseStatement.numLocalVariables(vd);
    }

    @Override
    public int maxBlockDepth() {
        int trueFalseMax = Math.max(trueStatement.maxBlockDepth(), falseStatement.maxBlockDepth());
        return condition.maxBlockDepth() + trueFalseMax;
    }

    @Override
    public void addConstantPoolEntries(ConstantPool cp) {
        condition.addConstantPoolEntries(cp);
        trueStatement.addConstantPoolEntries(cp);
        falseStatement.addConstantPoolEntries(cp);
    }

    @Override
    public List<Byte> generateCode(ConstantPool cp, Map<String, Variable> variables) {
        List<Byte> trueBytes = trueStatement.generateCode(cp, variables);
        List<Byte> falseBytes = falseStatement.generateCode(cp, variables);
        List<Byte> conditionBytes = condition.generateCode(cp, variables);

        ArrayList<Byte> bytes = new ArrayList<>();
        bytes.addAll(conditionBytes); // comparison

        int gotoLength = 3;
        int ifLength = 3;
        int jumpOverTrueLength = ifLength + gotoLength + trueBytes.size();
        if(jumpOverTrueLength > Short.MAX_VALUE) {
            throw new RuntimeException("Branch length too big");
        }

        int jumpOverFalseLength = gotoLength + falseBytes.size();
        if(jumpOverFalseLength > Short.MAX_VALUE) {
            throw new RuntimeException("Goto length too big");
        }
        bytes.add((byte) 153); // ifeq
        bytes.add((byte) (jumpOverTrueLength >> 8));
        bytes.add((byte) jumpOverTrueLength);
        bytes.addAll(trueBytes);
        bytes.add((byte) 167); // goto

        bytes.add((byte) (jumpOverFalseLength >> 8));
        bytes.add((byte) jumpOverFalseLength);

        bytes.addAll(falseBytes);
        return bytes;
    }
}
