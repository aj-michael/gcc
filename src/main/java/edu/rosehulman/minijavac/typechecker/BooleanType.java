package edu.rosehulman.minijavac.typechecker;

public class BooleanType extends PrimitiveType {

    BooleanType() {
        super("boolean");
    }

    @Override
    public String getDescriptor() {
        return "Z";
    }
}
