package edu.rosehulman.minijavac.ast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import edu.rosehulman.minijavac.generator.ConstantPool;
import edu.rosehulman.minijavac.typechecker.Scope;
import edu.rosehulman.minijavac.typechecker.Type;

public class AssignmentStatement implements Statement {
    public final Optional<Type> type;
    public final String id;
    public final Expression expression;

    public AssignmentStatement(String type, String id, Expression expression) {
        this.type = Optional.of(new Type(type));
        this.id = id;
        this.expression = expression;
    }

    public AssignmentStatement(String id, Expression expression) {
        this.type = Optional.empty();
        this.id = id;
        this.expression = expression;
    }

    public boolean isDeclaration() {
        return type.isPresent();
    }

    @Override
    public List<String> typecheck(Scope scope) {
        List<String> errors = new ArrayList<>();
        errors.addAll(expression.typecheck(scope));
        if (isDeclaration()) {
            if (scope.containsLocalVariable(id)) {
                errors.add("The variable " + id + " is already declared in the current scope.");
            }
            if (expression.getType(scope) == null) {
                errors.add("Variable this is not declared.");
            } else if (!expression.getType(scope).isA(type.get(), scope)) {
                errors.add("Cannot assign type " + expression.getType(scope) + " to variable " + id +
                    " of type " + type.get());
            }
            scope.declaredVariables.put(id, type.get());
        } else {
            if (!scope.containsVariable(id)) {
                errors.add("No variable named " + id + " exists in the current scope.");
            } else if (!expression.getType(scope).isA(scope.getVariableType(id), scope)) {
                errors.add("Cannot assign type " + expression.getType(scope) + " to variable " + id +
                    " of type " + scope.getVariableType(id));
            }
        }
        return errors;
    }

    @Override
    public int numLocalVariables(List<String> vd) {
        if(isDeclaration()) {
            vd.add(id);
            return 1;
        } else {
            return 0;
        }
    }

    public List<Byte> generateCode(ConstantPool cp, Map<String, Integer> variables) {
        ArrayList<Byte> bytes = new ArrayList<>();
        bytes.addAll(expression.generateCode(cp, variables));
        if(cp.thisFieldRefEntryMap.containsKey(id)) {
            bytes.add((byte) 181); // putfield
            bytes.add((byte) (cp.thisFieldRefEntryMap.get(id).index >> 8));
            bytes.add((byte) cp.thisFieldRefEntryMap.get(id).index);
        } else if(variables.containsKey(id)) {
            bytes.add((byte) 54); // istore
            bytes.add(variables.get(id).byteValue());
        } else {
            throw new RuntimeException("Couldn't find variable " + id);
        }
        return bytes;
    }
}
