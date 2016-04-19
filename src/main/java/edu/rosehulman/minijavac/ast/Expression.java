package edu.rosehulman.minijavac.ast;

import edu.rosehulman.minijavac.typechecker.Scope;
import edu.rosehulman.minijavac.typechecker.Type;

import java.util.List;

public interface Expression {
    List<String> typecheck(Scope scope);
    Type getType(Scope scope);
}
