package edu.rosehulman.minijavac.operators;

import edu.rosehulman.minijavac.typechecker.Type;

import java.util.List;

public interface Operator {
    public Type returnType(Type operationType);
    public List<Byte> getOperatorBytes(Type operationType);
    public boolean isOperationSupported(Type operationType);
}
