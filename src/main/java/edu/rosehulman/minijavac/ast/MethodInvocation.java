package edu.rosehulman.minijavac.ast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;
import edu.rosehulman.minijavac.generator.ConstantPool;
import edu.rosehulman.minijavac.generator.MethodRefEntry;
import edu.rosehulman.minijavac.generator.Variable;
import edu.rosehulman.minijavac.typechecker.Scope;
import edu.rosehulman.minijavac.typechecker.Type;

public class MethodInvocation implements CallExpression {
    public final CallExpression subject;
    public final String methodName;
    public final List<Expression> arguments;
    public Type subjectType;
    public String methodDescriptor;

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
            subjectType = subject.getType(scope);
            methodDescriptor = md.getDescriptor();

            for (int argIndex = 0; argIndex < arguments.size() && argIndex < md.arguments.size(); argIndex++) {
                Type realType = arguments.get(argIndex).getType(scope);
                Type requiredType = md.arguments.get(argIndex).type;
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
            return scope.getClassScope(subject.getType(scope).type).getMethod(methodName).returnType;
        } catch (NullPointerException e) {
            return Type.NULL;
        }
    }

    @Override
    public void addConstantPoolEntries(ConstantPool cp) {
        subject.addConstantPoolEntries(cp);
        arguments.forEach(a -> a.addConstantPoolEntries(cp));
        cp.methodRefEntry(subjectType.getDescriptor(), methodName, methodDescriptor);
    }

    @Override
    public List<Byte> generateCode(ConstantPool cp, Map<String, Variable> variables) {
        List<Byte> bytes = new ArrayList<>();
        bytes.addAll(subject.generateCode(cp, variables));
        bytes.add((byte) 182);      // invokevirtual
        MethodRefEntry methodRefEntry =
            cp.methodRefEntry(subjectType.getDescriptor(), methodName, methodDescriptor);
        bytes.add((byte) (methodRefEntry.index >> 8));
        bytes.add((byte) methodRefEntry.index);
        return bytes;
    }
}
