package edu.rosehulman.minijavac.operators;

import edu.rosehulman.minijavac.typechecker.Type;

import java.util.List;

public class Plus implements Operator {
    @Override
    public String toString() {
        return "PLUS";
    }

    @Override
    public Type returnType(Type operationType) {
        return operationType;
    }

    @Override
    public List<Byte> getOperatorBytes(Type operationType) {
        return operationType.plus();
    }

    @Override
    public boolean isOperationSupported(Type operationType) {
        return operationType.isPlusSupported();
    }
}
