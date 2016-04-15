package edu.rosehulman.minijavac.ast;

import java.util.Optional;

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
}
