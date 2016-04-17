package edu.rosehulman.minijavac.ast;

import java.util.ArrayList;
import java.util.List;

import edu.rosehulman.minijavac.typechecker.Scope;

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
        MethodDeclaration md = scope.getClassScope(subject.getType(scope)).getMethod(methodName);
        for (int argIndex = 0; argIndex < arguments.size(); argIndex++) {
            String realType = arguments.get(argIndex).getType(scope);
            String requiredType = md.arguments.get(argIndex).type;
            if (!realType.equals(requiredType)) {
                errors.add("Argument type " + realType +
                    " is incompatible with formal parameter type " + requiredType);
            }
        }
        return errors;
    }

    @Override
    public String getType(Scope scope) {
        try {
            return scope.getClassScope(subject.getType(scope)).getMethod(methodName).returnType;
        } catch (NullPointerException e) {
            return "no such method";
        }
    }
}
