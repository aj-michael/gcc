package edu.rosehulman.minijavac.typechecker;

public class ReferenceType extends Type{

    ReferenceType(String type) {
        super(type);
    }

    @Override
    public boolean isPrimitiveType() {
        return false;
    }

    @Override
    public String getDescriptor() {
        return type;
    }
}
