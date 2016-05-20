package edu.rosehulman.minijavac.ast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.rosehulman.minijavac.generator.ConstantPool;
import edu.rosehulman.minijavac.generator.Variable;
import edu.rosehulman.minijavac.typechecker.Scope;
import edu.rosehulman.minijavac.typechecker.Type;

public class ThreadSleepStatement implements Statement {
    public final Expression expression;
    public Type expressionType;

    public ThreadSleepStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public List<String> typecheck(Scope scope) {
        List<String> errors = new ArrayList<>();
        errors.addAll(expression.typecheck(scope));
        expressionType = expression.getType(scope);
        if (!(expressionType.isA(Type.LONG, scope))) {
            errors.add("Thread.sleep only takes a long. The expression has type " + expressionType);
        }
        return errors;
    }

    @Override
    public int numLocalVariables(List<Variable> vd) {
        return 0;
    }

    @Override
    public void addConstantPoolEntries(ConstantPool cp) {
        expression.addConstantPoolEntries(cp);
    }

    @Override
    public List<Byte> generateCode(ConstantPool cp, Map<String, Variable> variables) {
        ArrayList<Byte> bytes = new ArrayList<>();
        bytes.addAll(expression.generateCode(cp, variables));
        bytes.add((byte) 184); // invokestatic
        bytes.add((byte) (cp.threadSleepEntry.index >> 8));
        bytes.add((byte) cp.threadSleepEntry.index);
        return bytes;
    }

    @Override
    public int maxBlockDepth() {
        return expression.maxBlockDepth() + 1;
    }
}
