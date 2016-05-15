package edu.rosehulman.minijavac.operators;

import edu.rosehulman.minijavac.typechecker.Type;

import java.util.List;

public class GreaterThan implements Operator {
    @Override
    public String toString() {
        return "GT";
    }

    @Override
    public Type returnType(Type operationType) {
        return Type.BOOLEAN;
    }

    @Override
    public List<Byte> getOperatorBytes(Type operationType) {
        return operationType.greaterThan();
    }

    @Override
    public boolean isOperationSupported(Type operationType) {
        return operationType.isGreaterThanSupported();
    }
}
