package edu.rosehulman.minijavac.ast;

import com.google.common.collect.ImmutableList;
import edu.rosehulman.minijavac.typechecker.Scope;

import java.util.ArrayList;
import java.util.List;

public class WhileStatement implements Statement {
    public final Expression condition;
    public final Statement statement;

    public WhileStatement(Expression condition, Statement statement) {
        this.condition = condition;
        this.statement = statement;
    }

    @Override
    public List<Statement> getSubstatements() {
        return ImmutableList.of(statement);
    }

    @Override
    public List<String> typecheck(Scope scope) {
        List<String> errors = new ArrayList<>();
        String conditionType = condition.getType(scope);
        if (!conditionType.equals("boolean")) {
           errors.add("While loop condition must be a boolean.  The expressions has type " + conditionType);
        }
        return errors;
    }
}
