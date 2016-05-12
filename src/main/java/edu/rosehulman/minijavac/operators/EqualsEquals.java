package edu.rosehulman.minijavac.operators;

import edu.rosehulman.minijavac.typechecker.Type;

import java.util.List;

public class EqualsEquals implements Operator {
    @Override
    public Type returnType(Type operationType) {
        return Type.BOOLEAN;
    }

    @Override
    public List<Byte> getOperatorBytes(Type operationType) {
        return operationType.equalsEquals();
    }

    @Override
    public boolean isOperationSupported(Type operationType) {
        return operationType.isEqualsEqualsSupported();
    }

    @Override
    public String toString() {
        return "EQ";
    }
}
