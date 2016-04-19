package edu.rosehulman.minijavac.ast;

import java.util.ArrayList;
import java.util.List;

import edu.rosehulman.minijavac.typechecker.Scope;
import edu.rosehulman.minijavac.typechecker.Type;

public class PrintlnStatement implements Statement {
    public final Expression expression;

    public PrintlnStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public List<String> typecheck(Scope scope) {
        List<String> errors = new ArrayList<>();
        errors.addAll(expression.typecheck(scope));
        Type expressionType = expression.getType(scope);
        if (!expressionType.isA(Type.INT, scope)) {
            errors.add("In MiniJava, System.out.println only takes an int.  The expression has type " + expressionType);
        }
        return errors;
    }
}
