package edu.rosehulman.minijavac.ast;

import com.google.common.collect.ImmutableList;
import edu.rosehulman.minijavac.typechecker.Scope;

import java.util.ArrayList;
import java.util.List;

public class PrintlnStatement implements Statement {
    public final Expression expression;

    public PrintlnStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public List<Statement> getSubstatements() {
        return ImmutableList.of();
    }

    @Override
    public List<String> typecheck(Scope scope) {
        List<String> errors = new ArrayList<>();
        errors.addAll(expression.typecheck(scope));
        String expressionType = expression.getType(scope);
        if (!expressionType.equals("int")) {
            errors.add("In MiniJava, System.out.println only takes an int.  The expression has type " + expressionType);
        }
        return errors;
    }
}
