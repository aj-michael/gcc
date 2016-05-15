package edu.rosehulman.minijavac.operators;

import edu.rosehulman.minijavac.typechecker.Type;

import java.util.List;

public class GreaterThanOrEqualTo implements Operator {
    @Override
    public String toString() {
        return "GTE";
    }

    @Override
    public Type returnType(Type operationType) {
        return Type.BOOLEAN;
    }

    @Override
    public List<Byte> getOperatorBytes(Type operationType) {
        return operationType.greaterThanOrEqualTo();
    }

    @Override
    public boolean isOperationSupported(Type operationType) {
        return operationType.isGreaterThanOrEqualToSupported();
    }
}
