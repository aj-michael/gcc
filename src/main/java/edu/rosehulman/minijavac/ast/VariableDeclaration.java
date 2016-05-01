package edu.rosehulman.minijavac.ast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;
import edu.rosehulman.minijavac.generator.ConstantPool;
import edu.rosehulman.minijavac.generator.Variable;
import edu.rosehulman.minijavac.typechecker.Scope;
import edu.rosehulman.minijavac.typechecker.Type;

public class VariableDeclaration implements Statement {
    public final String name;
    final Type type;

    public VariableDeclaration(String name, String type) {
        this.name = name;
        this.type = Type.of(type);
    }

    @Override
    public List<String> typecheck(Scope scope) {
        List<String> errors = new ArrayList<>();
        return errors;
    }

    @Override
    public int numLocalVariables(List<Variable> vd) {
        vd.add(new Variable(name, type, vd.size()+1));
        return 1;
    }

    @Override
    public void addConstantPoolEntries(ConstantPool cp) {
        cp.classEntry(type.getDescriptor());
    }

    @Override
    public List<Byte> generateCode(ConstantPool cp, Map<String, Variable> variables) {
        return ImmutableList.of();
    }

    public String getDescriptor() {
        if (type == Type.NULL || type.isPrimitiveType()) {
            return type.getDescriptor();
        } else {
            return "L" + type.getDescriptor() + ";";
        }
    }
}
