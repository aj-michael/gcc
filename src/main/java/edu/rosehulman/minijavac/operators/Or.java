package edu.rosehulman.minijavac.operators;

import edu.rosehulman.minijavac.typechecker.Type;

import java.util.List;

public class Or implements Operator {
    @Override
    public String toString() {
        return "OR";
    }

    @Override
    public Type returnType(Type operationType) {
        return Type.BOOLEAN;
    }

    @Override
    public List<Byte> getOperatorBytes(Type operationType) {
        return operationType.or();
    }

    @Override
    public boolean isOperationSupported(Type operationType) {
        return operationType.isOrSupported();
    }
}
