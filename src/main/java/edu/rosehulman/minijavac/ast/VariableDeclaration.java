package edu.rosehulman.minijavac.ast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;
import edu.rosehulman.minijavac.generator.ConstantPool;
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
    public int numLocalVariables(List<String> vd) {
        vd.add(name);
        return 1;
    }

    @Override
    public void addConstantPoolEntries(ConstantPool cp) {
        cp.classEntry(type.getDescriptor());
    }

    @Override
    public List<Byte> generateCode(ConstantPool cp, Map<String, Integer> variables) {
        return ImmutableList.of();
    }

    public String getDescriptor() {
        if(type.equals("int")) {
            return "I";
        } else if(type.equals("boolean")) {
            return "Z";
        } else {
            return "L" + type + ";";
        }
    }
}
