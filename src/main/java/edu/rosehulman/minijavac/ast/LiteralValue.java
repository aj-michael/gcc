package edu.rosehulman.minijavac.ast;

import edu.rosehulman.minijavac.typechecker.Scope;

import java.util.ArrayList;
import java.util.List;

public class LiteralValue implements LiteralExpression {
    public final String type;
    public final Object value;

    public LiteralValue(String type, Object value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public List<String> typecheck(Scope scope) {
        List<String> errors = new ArrayList<>();
        return errors;
    }

    @Override
    public String getType(Scope scope) {
        return type;
    }
}
