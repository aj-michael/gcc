package edu.rosehulman.minijavac.ast;

import edu.rosehulman.minijavac.typechecker.Scope;

import java.util.List;

public interface Expression {
    List<String> typecheck(Scope scope);
    String getType(Scope scope);
}
