package edu.rosehulman.minijavac.ast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.rosehulman.minijavac.generator.ConstantPool;
import edu.rosehulman.minijavac.typechecker.Scope;
import edu.rosehulman.minijavac.typechecker.Type;

public class PrintlnStatement implements Statement {
    public final Expression expression;

    public PrintlnStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public List<String> typecheck(Scope scope) {
        List<String> errors = new ArrayList<>();
        errors.addAll(expression.typecheck(scope));
        Type expressionType = expression.getType(scope);
        if (!expressionType.isA(Type.INT, scope)) {
            errors.add("In MiniJava, System.out.println only takes an int.  The expression has type " + expressionType);
        }
        return errors;
    }

    @Override
    public int numLocalVariables(List<String> vd) {
        return 0;
    }

    @Override
    public List<Byte> generateCode(ConstantPool cp, Map<String, Integer> variables) {
        ArrayList<Byte> bytes = new ArrayList<>();
        bytes.add((byte) 178); // getstatic
        bytes.add((byte) (cp.systemOutEntry.index >> 8));
        bytes.add((byte) cp.systemOutEntry.index);
        List<Byte> expBytes = expression.generateCode(cp, variables);
        bytes.addAll(expBytes);
        bytes.add((byte) 182); // invokevirtual
        bytes.add((byte) (cp.printlnEntry.index >> 8));
        bytes.add((byte) cp.printlnEntry.index);
        return bytes;
    }
}
