package edu.rosehulman.minijavac.ast;

import java.util.ArrayList;
import java.util.List;

import edu.rosehulman.minijavac.typechecker.Scope;
import edu.rosehulman.minijavac.typechecker.Type;

public class MethodInvocation implements CallExpression {
    public final CallExpression subject;
    public final String methodName;
    public final List<Expression> arguments;

    public MethodInvocation(CallExpression subject, String methodName, List<Expression> arguments) {
        this.subject = subject;
        this.methodName = methodName;
        this.arguments = arguments;
    }

    @Override
    public List<String> typecheck(Scope scope) {
        List<String> errors = new ArrayList<>();
        if (subject.getType(scope) == null) {
            errors.add("Variable this is not declared.");
        }
        for (Expression argument : arguments) {
            if (argument.getType(scope) == null) {
                errors.add("Variable this is not declared.");
            }
            errors.addAll(argument.typecheck(scope));
        }

        errors.addAll(subject.typecheck(scope));

        Scope classScope = scope.getClassScope(subject.getType(scope).type);
        if (classScope == null || !classScope.containsMethod(methodName)) {
            errors.add("No method named " + methodName + " found for class " + subject.getType(scope));
        } else {
            MethodDeclaration md = classScope.getMethod(methodName);
            for (int argIndex = 0; argIndex < arguments.size() && argIndex < md.arguments.size(); argIndex++) {
                Type realType = arguments.get(argIndex).getType(scope);
                Type requiredType = new Type(md.arguments.get(argIndex).type);
                if (arguments.get(argIndex).getType(scope) == null) {
                    // Already added `this not declared` error.
                } else if (!realType.isA(requiredType, scope)) {
                    errors.add("Argument type " + realType +
                        " is incompatible with formal parameter type " + requiredType);
                }
            }
        }
        return errors;
    }

    @Override
    public Type getType(Scope scope) {
        try {
            return new Type(scope.getClassScope(subject.getType(scope).type).getMethod(methodName).returnType);
        } catch (NullPointerException e) {
            return Type.NULL;
        }
    }
}
