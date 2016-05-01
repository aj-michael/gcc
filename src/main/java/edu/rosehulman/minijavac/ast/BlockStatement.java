package edu.rosehulman.minijavac.ast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.rosehulman.minijavac.generator.ConstantPool;
import edu.rosehulman.minijavac.generator.Variable;
import edu.rosehulman.minijavac.typechecker.Scope;

public class BlockStatement implements Statement {
    public final List<Statement> statements;

    public BlockStatement(List<Statement> statements) {
        this.statements = statements;
    }

    @Override
    public List<String> typecheck(Scope scope) {
        List<String> errors = new ArrayList<>();
        Scope blockScope = new Scope(scope);

        for (Statement statement : statements) {
            errors.addAll(statement.typecheck(blockScope));
        }

        return errors;
    }

    @Override
    public int numLocalVariables(List<Variable> vd) {
        int num = 0;
        for(Statement statement : statements) {
            num += statement.numLocalVariables(vd);
        }
        return num;
    }

    @Override
    public int maxBlockDepth() {
        int depth = 0;
        for(Statement statement : statements) {
            depth += statement.maxBlockDepth();
        }
        return depth;
    }

    @Override
    public void addConstantPoolEntries(ConstantPool cp) {
        statements.forEach(s -> s.addConstantPoolEntries(cp));
    }

    @Override
    public List<Byte> generateCode(ConstantPool cp, Map<String, Variable> variables) {
        ArrayList<Byte> bytes = new ArrayList<>();
        for(Statement statement : statements) {
           bytes.addAll(statement.generateCode(cp, variables));
        }
        return bytes;
    }
}
