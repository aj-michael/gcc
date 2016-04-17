package edu.rosehulman.minijavac.ast;

import edu.rosehulman.minijavac.typechecker.Scope;

import java.util.ArrayList;
import java.util.List;

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
