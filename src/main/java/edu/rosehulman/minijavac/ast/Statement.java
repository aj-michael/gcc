package edu.rosehulman.minijavac.ast;

import edu.rosehulman.minijavac.generator.ConstantPool;
import edu.rosehulman.minijavac.typechecker.Scope;

import java.util.List;
import java.util.Map;

public interface Statement {
    List<String> typecheck(Scope scope);
    int numLocalVariables(List<String> vd);
    default int maxBlockDepth() { return 0; }
    void addConstantPoolEntries(ConstantPool cp);
    List<Byte> generateCode(ConstantPool cp, Map<String, Integer> variables);
}
