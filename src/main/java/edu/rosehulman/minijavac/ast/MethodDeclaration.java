package edu.rosehulman.minijavac.ast;

import com.google.common.collect.ImmutableMap;
import edu.rosehulman.minijavac.typechecker.Scope;
import edu.rosehulman.minijavac.typechecker.Type;

import java.util.*;

public class MethodDeclaration {
    public final String name;
    public final String returnType;
    public final List<VariableDeclaration> arguments;
    public final List<Statement> statements;
    public final Expression returnExpression;

    public MethodDeclaration(String name, List<Statement> statements) {
        this(name, new LinkedList<>(), statements, null, null);
    }

    public MethodDeclaration(String name, List<VariableDeclaration> arguments, List<Statement> statements, String returnType, Expression returnExpression) {
        this.name = name;
        this.returnType = returnType;
        this.arguments = arguments;
        this.statements = statements;
        this.returnExpression = returnExpression;
    }

    public boolean canOverride(MethodDeclaration other) {
        Iterator<VariableDeclaration> arguments = this.arguments.iterator();
        Iterator<VariableDeclaration> otherArguments = other.arguments.iterator();
        while (arguments.hasNext() && otherArguments.hasNext()) {
            if (!arguments.next().type.equals(otherArguments.next().type)) {
                return false;
            }
        }
        if (arguments.hasNext() || otherArguments.hasNext()) {
            return false;
        }
        return returnType.equals(other.returnType);
    }

    public List<String> typecheck(Scope scope) {
        List<String> errors = new ArrayList<>();

        Set<String> argumentNames = new HashSet<String>();
        for (VariableDeclaration argument : arguments) {
            if (argumentNames.contains(argument.name)) {
                errors.add("Formal parameter named " + argument.name + " duplicates the name of another formal parameter.");
            } else if (!scope.containsClass(argument.type)) {
                errors.add("Cannot find class named " + argument.type);
            } else {
                scope.declaredVariables.put(argument.name, new Type(argument.type));
            }
            argumentNames.add(argument.name);
        }
        for (Statement statement : statements) {
            errors.addAll(statement.typecheck(scope));
        }
        if (returnExpression != null) {
            errors.addAll(returnExpression.typecheck(scope));
        }
        if ((returnExpression != null) && !returnExpression.getType(scope).isA(new Type(returnType), scope)) {
            errors.add(
                "Actual return type " + returnExpression.getType(scope) + " of method " + name +
                    " does not match declared type " + returnType);
        }
        return errors;
    }

    private static Map<String, String> primitiveTypes = ImmutableMap.of(
            "int", "I",
            "null", "V",
            "boolean", "Z"
    );

    private static String formatType(String type) {
        if (type == null) {
            return "V";
        } else if (primitiveTypes.containsKey(type)) {
            return primitiveTypes.get(type);
        } else {
            return "L" + type;
        }
    }

    public String getDescriptor() {
        StringBuilder builder = new StringBuilder()
            .append("(");
        for (VariableDeclaration argument : arguments) {
            builder.append(formatType(argument.type))
                .append(";");
        }
        builder.append(")")
            .append(formatType(returnType));
        return builder.toString();
    }
}
