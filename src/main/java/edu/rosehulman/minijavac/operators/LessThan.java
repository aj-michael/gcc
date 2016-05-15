package edu.rosehulman.minijavac.operators;

import edu.rosehulman.minijavac.typechecker.Type;

import java.util.List;

public class LessThan implements Operator {
    @Override
    public String toString() {
        return "LT";
    }

    @Override
    public Type returnType(Type operationType) {
        return Type.BOOLEAN;
    }

    @Override
    public List<Byte> getOperatorBytes(Type operationType) {
        return operationType.lessThan();
    }

    @Override
    public boolean isOperationSupported(Type operationType) {
        return operationType.isLessThanSupported();
    }
}
