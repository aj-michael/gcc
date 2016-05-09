package edu.rosehulman.minijavac.typechecker;

public class NullType extends ReferenceType {

    NullType() {
        super("null");
    }

    @Override
    public String getDescriptor() {
        return "V";
    }
}
