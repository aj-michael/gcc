package edu.rosehulman.minijavac.ast;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import edu.rosehulman.minijavac.typechecker.Scope;

public class AssignmentStatement implements Statement {
    public final Optional<String> type;
    public final String id;
    public final Expression expression;

    public AssignmentStatement(String type, String id, Expression expression) {
        this.type = Optional.of(type);
        this.id = id;
        this.expression = expression;
    }

    public AssignmentStatement(String id, Expression expression) {
        this.type = Optional.empty();
        this.id = id;
        this.expression = expression;
    }

    public boolean isDeclaration() {
        return type.isPresent();
    }

    @Override
    public List<String> typecheck(Scope scope) {
        List<String> errors = new ArrayList<>();
        errors.addAll(expression.typecheck(scope));
        if (isDeclaration()) {
            if (scope.containsVariable(id)) {
                errors.add("The variable " + id + " is already declared in the current scope.");
            } else if (!scope.containsClass(type.get())) {
                errors.add("Cannot find class named " + type.get());
            } else if (expression.getType(scope) == null) {
                errors.add("Variable this is not declared.");
            } else if (type.get().equals(expression.getType(scope)) || expression.getType(scope).equals("null")) {
                scope.declaredVariables.put(id, type.get());
            } else {
                errors.add("Cannot assign type " + expression.getType(scope) + " to variable " + id +
                    " of type " + type.get());
            }
        } else {
            if (!scope.containsVariable(id)) {
                errors.add("Variable " + id + " is not declared.");
            } else if (!scope.getVariableType(id).equals(expression.getType(scope)) && !expression.getType(scope).equals("null")) {
                errors.add("Cannot assign type " + expression.getType(scope) + " to variable " + id +
                    " of type " + scope.getVariableType(id));
            }
        }
        return errors;
    }
}
