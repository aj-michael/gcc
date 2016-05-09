package edu.rosehulman.minijavac.ast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.rosehulman.minijavac.generator.ConstantPool;
import edu.rosehulman.minijavac.generator.Variable;
import edu.rosehulman.minijavac.typechecker.Scope;
import edu.rosehulman.minijavac.typechecker.Type;

public class VariableInvocation implements LiteralExpression {
    public final String name;

    public VariableInvocation(String name) {
        this.name = name;
    }

    @Override
    public List<String> typecheck(Scope scope) {
        List<String> errors = new ArrayList<>();
        if (!scope.containsVariable(name)) {
            errors.add("Variable " + name + " is not declared.");
        }
        return errors;
    }

    @Override
    public Type getType(Scope scope) {
        if (scope.getVariableType(name) != null) {
            return scope.getVariableType(name);
        } else {
            return Type.NULL;
        }
    }

    @Override
    public void addConstantPoolEntries(ConstantPool cp) {
    }

    @Override
    public int maxBlockDepth() {
        return 1;
    }

    @Override
    public List<Byte> generateCode(ConstantPool cp, Map<String, Variable> variables) {
        if(cp.thisFieldRefEntryMap.containsKey(name)) {
            ArrayList<Byte> bytes = new ArrayList<>();
            bytes.add((byte) 42); // aload_0
            bytes.add((byte) 180); // getfield
            bytes.add((byte) (cp.thisFieldRefEntryMap.get(name).index >> 8));
            bytes.add((byte) cp.thisFieldRefEntryMap.get(name).index);
            return bytes;
        } else if(variables.containsKey(name)) {
            ArrayList<Byte> bytes = new ArrayList<>();
            Variable variable = variables.get(name);
            bytes.addAll(variable.getType().load(variable.getPosition().byteValue()));
            return bytes;
        } else {
            throw new RuntimeException("Could not find variable: " + name);
        }
    }
}
