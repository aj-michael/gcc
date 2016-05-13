package edu.rosehulman.minijavac.typechecker;

public abstract class PrimitiveType extends Type {

    PrimitiveType(String type) {
        super(type);
    }

    @Override
    public boolean isPrimitiveType() {
        return true;
    }
}
