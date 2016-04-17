package edu.rosehulman.minijavac.ast;

import java.util.ArrayList;
import java.util.List;

import edu.rosehulman.minijavac.typechecker.Scope;

public class VariableDeclaration implements Statement {
    public final String name;
    public final String type;

    public VariableDeclaration(String name, String type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public List<String> typecheck(Scope scope) {
        List<String> errors = new ArrayList<>();
        return errors;
    }
}
