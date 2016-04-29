package edu.rosehulman.minijavac.ast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.rosehulman.minijavac.generator.ConstantPool;
import edu.rosehulman.minijavac.typechecker.Scope;
import edu.rosehulman.minijavac.typechecker.Type;

public class ParenthesisExpression implements LiteralExpression {
    public final Expression expression;

    public ParenthesisExpression(Expression expression) {
        this.expression = expression;
    }

    @Override
    public List<String> typecheck(Scope scope) {
        List<String> errors = new ArrayList<>();
        errors.addAll(expression.typecheck(scope));
        return errors;
    }

    @Override
    public Type getType(Scope scope) {
        return expression.getType(scope);
    }

    @Override
    public void addIntegerEntries(ConstantPool cp) {
        expression.addIntegerEntries(cp);
    }

    @Override
    public List<Byte> generateCode(ConstantPool cp, Map<String, Integer> variables) {
        return expression.generateCode(cp, variables);
    }
}
