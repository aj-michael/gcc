package edu.rosehulman.minijavac.ast;

import edu.rosehulman.minijavac.generator.ConstantPool;
import edu.rosehulman.minijavac.generator.Variable;
import edu.rosehulman.minijavac.typechecker.Scope;
import edu.rosehulman.minijavac.typechecker.Type;

import java.util.List;
import java.util.Map;

public interface Expression {
    List<String> typecheck(Scope scope);
    Type getType(Scope scope);
    void addConstantPoolEntries(ConstantPool cp);
    List<Byte> generateCode(ConstantPool cp, Map<String, Variable> variables);
    default int maxBlockDepth() { return 0; }
}
