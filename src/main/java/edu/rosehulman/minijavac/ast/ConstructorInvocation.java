package edu.rosehulman.minijavac.ast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.rosehulman.minijavac.generator.ConstantPool;
import edu.rosehulman.minijavac.typechecker.Scope;
import edu.rosehulman.minijavac.typechecker.Type;

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
    public Type getType(Scope scope) {
        return new Type(className);
    }

    @Override
    public List<Byte> generateCode(ConstantPool cp, Map<String, Integer> variables) {
        return new ArrayList<>();
    }
}
