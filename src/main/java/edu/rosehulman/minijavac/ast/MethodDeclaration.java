package edu.rosehulman.minijavac.ast;

import edu.rosehulman.minijavac.typechecker.Scope;
import edu.rosehulman.minijavac.typechecker.VariableRedeclaration;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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

        if (!(this instanceof MainMethodDeclaration) && !scope.containsClass(returnType)) {
            errors.add("Cannot find class named " + returnType);
        }

        for (VariableDeclaration argument : arguments) {
            if (scope.containsVariable(argument.name)) {
                errors.add("Formal parameter named " + argument.name + " duplicates the name of another formal parameter.");
            } else if (!scope.containsClass(argument.type)) {
                errors.add("Cannot find class named " + argument.type);
            } else {
                scope.declaredVariables.put(argument.name, argument.type);
            }
        }

        for (Statement statement : statements) {
            errors.addAll(statement.typecheck(scope));
        }

        if (returnExpression != null && !returnExpression.getType(scope).equals(returnType)) {
            errors.add("Actual return type " + returnExpression.getType(scope) + " of method " + name +
                " does not match declared type " + returnType);
        }

        return errors;
    }
}
