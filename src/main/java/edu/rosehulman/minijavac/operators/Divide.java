package edu.rosehulman.minijavac.operators;

import edu.rosehulman.minijavac.typechecker.Type;

import java.util.List;

public class Divide implements Operator {
    @Override
    public Type returnType(Type operationType) {
        return operationType;
    }

    @Override
    public List<Byte> getOperatorBytes(Type operationType) {
        return operationType.divide();
    }

    @Override
    public boolean isOperationSupported(Type operationType) {
        return operationType.isDivideSupported();
    }

    @Override
    public String toString() {
        return "DIVIDE";
    }
}
