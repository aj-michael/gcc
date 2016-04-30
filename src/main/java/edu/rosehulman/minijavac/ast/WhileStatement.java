package edu.rosehulman.minijavac.ast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.rosehulman.minijavac.generator.ConstantPool;
import edu.rosehulman.minijavac.typechecker.Scope;
import edu.rosehulman.minijavac.typechecker.Type;

public class WhileStatement implements Statement {
    public final Expression condition;
    public final Statement statement;

    public WhileStatement(Expression condition, Statement statement) {
        this.condition = condition;
        this.statement = statement;
    }

    @Override
    public List<String> typecheck(Scope scope) {
        List<String> errors = new ArrayList<>();
        Type conditionType = condition.getType(scope);
        if (!conditionType.isA(Type.BOOLEAN, scope)) {
           errors.add("While loop condition must be a boolean.  The expressions has type " + conditionType);
        }
        errors.addAll(condition.typecheck(scope));
        errors.addAll(statement.typecheck(scope));
        return errors;
    }

    @Override
    public int numLocalVariables(List<String> vd) {
        return statement.numLocalVariables(vd);
    }

    @Override
    public int maxBlockDepth() {
        return Math.max(statement.maxBlockDepth(), condition.maxBlockDepth());
    }

    @Override
    public void addIntegerEntries(ConstantPool cp) {
        condition.addIntegerEntries(cp);
        statement.addIntegerEntries(cp);
    }

    @Override
    public List<Byte> generateCode(ConstantPool cp, Map<String, Integer> variables) {
        ArrayList<Byte> bytes = new ArrayList<>();
        List<Byte> conditionBytes = condition.generateCode(cp, variables);
        bytes.addAll(conditionBytes);
        List<Byte> statementBytes = statement.generateCode(cp, variables);
        bytes.add((byte) 153); // ifeq

        int ifLength = 3;
        int gotoLength = 3;
        int jumpOverLength = ifLength + gotoLength + statementBytes.size();
        if(jumpOverLength > Short.MAX_VALUE) {
            throw new RuntimeException("Branch length too big");
        }

        int jumpBackLength = -(jumpOverLength + conditionBytes.size() - gotoLength);

        bytes.add((byte) (jumpOverLength >> 8));
        bytes.add((byte) jumpOverLength);
        bytes.addAll(statementBytes);
        bytes.add((byte) 167); // goto
        bytes.add((byte) (jumpBackLength >> 8));
        bytes.add((byte) jumpBackLength);

        return bytes;
    }
}
