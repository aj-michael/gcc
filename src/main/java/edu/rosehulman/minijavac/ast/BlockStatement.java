package edu.rosehulman.minijavac.ast;

import edu.rosehulman.minijavac.typechecker.Scope;

import java.util.ArrayList;
import java.util.List;

public class BlockStatement implements Statement {
    public final List<Statement> statements;

    public BlockStatement(List<Statement> statements) {
        this.statements = statements;
    }

    @Override
    public List<Statement> getSubstatements() {
        return statements;
    }

    @Override
    public List<String> typecheck(Scope scope) {
        List<String> errors = new ArrayList<>();
        Scope blockScope = new Scope(scope);

        for(Statement statement : statements) {
            errors.addAll(statement.typecheck(blockScope));
        }

        return errors;
    }
}
