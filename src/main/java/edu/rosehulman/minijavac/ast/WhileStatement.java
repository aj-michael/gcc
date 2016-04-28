package edu.rosehulman.minijavac.ast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;
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
    public int numLocalVariables(List<VariableDeclaration> vd) {
        return statement.numLocalVariables(vd);
    }

    @Override
    public int maxBlockDepth() {
        return statement.maxBlockDepth();
    }

    @Override
    public List<Byte> generateCode(ConstantPool cp, Map<String, Integer> variables) {
        return ImmutableList.of();
    }
}
