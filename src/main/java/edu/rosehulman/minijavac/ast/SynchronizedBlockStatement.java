package edu.rosehulman.minijavac.ast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import edu.rosehulman.minijavac.generator.ConstantPool;
import edu.rosehulman.minijavac.generator.Variable;
import edu.rosehulman.minijavac.typechecker.Scope;
import edu.rosehulman.minijavac.typechecker.Type;

public class SynchronizedBlockStatement implements Statement {
    public final Expression lock;
    public final String lockName;
    public final List<Statement> statements;

    public SynchronizedBlockStatement(Expression lock, List<Statement> statements) {
        this.lock = lock;
        lockName = UUID.randomUUID().toString();
        this.statements = statements;
    }

    @Override
    public List<String> typecheck(Scope scope) {
        List<String> errors = new ArrayList<>();

        Type lockType = lock.getType(scope);
        if (lockType.isPrimitiveType()) {
            errors.add("Cannot synchronize on primitive type " + lockType);
        }

        Scope blockScope = new Scope(scope);

        for (Statement statement : statements) {
            errors.addAll(statement.typecheck(blockScope));
        }

        return errors;
    }

    @Override
    public int numLocalVariables(List<Variable> vd) {
        int num = 0;
        for (Statement statement : statements) {
            num += statement.numLocalVariables(vd);
        }
        vd.add(new Variable(lockName, Type.of("java/lang/Object"), vd.size()+1));
        return num + 1;
    }

    @Override
    public int maxBlockDepth() {
        int depth = lock.maxBlockDepth();
        for (Statement statement : statements) {
            depth += statement.maxBlockDepth();
        }
        return depth;
    }

    @Override
    public void addConstantPoolEntries(ConstantPool cp) {
        lock.addConstantPoolEntries(cp);
        statements.forEach(s -> s.addConstantPoolEntries(cp));
    }

    @Override
    public List<Byte> generateCode(ConstantPool cp, Map<String, Variable> variables) {
        Variable lockVariable = variables.get(lockName);
        ArrayList<Byte> bytes = new ArrayList<>();
        bytes.addAll(lock.generateCode(cp, variables));
        bytes.add((byte) 89);   // dup
        bytes.add((byte) 58);   // astore
        bytes.add(lockVariable.getPosition().byteValue());
        bytes.add((byte) 194);  // monitorenter
        for (Statement statement : statements) {
           bytes.addAll(statement.generateCode(cp, variables));
        }
        bytes.add((byte) 25);   // aload
        bytes.add(lockVariable.getPosition().byteValue());
        bytes.add((byte) 195);  // monitorexit
        return bytes;
    }
}
