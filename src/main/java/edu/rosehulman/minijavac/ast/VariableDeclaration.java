package edu.rosehulman.minijavac.ast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;
import edu.rosehulman.minijavac.generator.ConstantPool;
import edu.rosehulman.minijavac.generator.Variable;
import edu.rosehulman.minijavac.typechecker.DoubleType;
import edu.rosehulman.minijavac.typechecker.LongType;
import edu.rosehulman.minijavac.typechecker.Scope;
import edu.rosehulman.minijavac.typechecker.Type;

public class VariableDeclaration implements Statement {
    public final String name;
    final Type type;
    final boolean isVolatile;

    public VariableDeclaration(String name, String type) {
        this(name, type, false);
    }

    public VariableDeclaration(String name, String type, boolean isVolatile) {
        this.name = name;
        this.type = Type.of(type);
        this.isVolatile = isVolatile;
    }

    @Override
    public List<String> typecheck(Scope scope) {
        List<String> errors = new ArrayList<>();
        return errors;
    }

    @Override
    public int numLocalVariables(List<Variable> vd) {
        vd.add(new Variable(name, type, vd.size()+1));
        if(type instanceof DoubleType || type instanceof LongType) {
            vd.add(null);
            return 2;
        }
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

    public short getAccessFlags() {
        short accessFlags = 0;
        accessFlags |= 0x0001;      // public
        if (isVolatile) {
            accessFlags |= 0x0040;  // volatile
        }
        return accessFlags;
    }
}
