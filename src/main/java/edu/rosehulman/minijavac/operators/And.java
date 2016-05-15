package edu.rosehulman.minijavac.operators;

import edu.rosehulman.minijavac.typechecker.Type;

import java.util.List;

public class And implements Operator {
    @Override
    public Type returnType(Type operationType) {
        return Type.BOOLEAN;
    }

    @Override
    public List<Byte> getOperatorBytes(Type operationType) {
        return operationType.and();
    }

    @Override
    public boolean isOperationSupported(Type operationType) {
        return operationType.isAndSupported();
    }

    @Override
    public String toString() {
        return "AND";
    }
}
