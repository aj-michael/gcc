package edu.rosehulman.minijavac.ast;

import java.util.ArrayList;
import java.util.List;

import edu.rosehulman.minijavac.typechecker.Scope;

public class ConstructorInvocation implements CallExpression {
    public final String className;

    public ConstructorInvocation(String className) {
        this.className = className;
    }

    @Override
    public List<String> typecheck(Scope scope) {
        List<String> errors = new ArrayList<>();
        if (!scope.containsClass(className)) {
            errors.add("Cannot instantiate undeclared class named " + className);
        }
        return errors;
    }

    @Override
    public String getType(Scope scope) {
        return className;
    }
}
