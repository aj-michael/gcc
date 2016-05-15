package edu.rosehulman.minijavac.operators;

import edu.rosehulman.minijavac.typechecker.Type;

import java.util.List;

public class Multiply implements Operator {
    @Override
    public Type returnType(Type operationType) {
        return operationType;
    }

    @Override
    public String toString() {
        return "MULTIPLY";
    }

    @Override
    public List<Byte> getOperatorBytes(Type operationType) {
        return operationType.multiply();
    }

    @Override
    public boolean isOperationSupported(Type operationType) {
        return operationType.isMultiplySupported();
    }
}
