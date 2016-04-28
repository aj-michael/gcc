package edu.rosehulman.minijavac.ast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;
import edu.rosehulman.minijavac.generator.ConstantPool;
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
    public int numLocalVariables(List<String> vd) {
        return trueStatement.numLocalVariables(vd) + falseStatement.numLocalVariables(vd);
    }

    @Override
    public int maxBlockDepth() {
        return Math.max(trueStatement.maxBlockDepth(), falseStatement.maxBlockDepth());
    }

    @Override
    public List<Byte> generateCode(ConstantPool cp, Map<String, Integer> variables) {
        List<Byte> trueBytes = trueStatement.generateCode(cp, variables);
        List<Byte> falseBytes = falseStatement.generateCode(cp, variables);

        ArrayList<Byte> bytes = new ArrayList<>();
        bytes.addAll(ImmutableList.of()); // comparison
        bytes.addAll(trueBytes);
        bytes.addAll(ImmutableList.of()); // goto
        bytes.addAll(falseBytes);
        return bytes;
    }
}
