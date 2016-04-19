package edu.rosehulman.minijavac.ast;

import java.util.ArrayList;
import java.util.List;

import edu.rosehulman.minijavac.typechecker.Scope;
import edu.rosehulman.minijavac.typechecker.Type;

public class VariableInvocation implements LiteralExpression {
    public final String name;

    public VariableInvocation(String name) {
        this.name = name;
    }

    @Override
    public List<String> typecheck(Scope scope) {
        List<String> errors = new ArrayList<>();
        if (!scope.containsVariable(name)) {
            errors.add("Variable " + name + " is not declared.");
        }
        return errors;
    }

    @Override
    public Type getType(Scope scope) {
        if (scope.getVariableType(name) != null) {
            return scope.getVariableType(name);
        } else {
            return Type.NULL;
        }
    }
}
