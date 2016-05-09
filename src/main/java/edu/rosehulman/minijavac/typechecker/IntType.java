package edu.rosehulman.minijavac.typechecker;

public class IntType extends PrimitiveType {

    IntType() {
        super("int");
    }

    @Override
    public String getDescriptor() {
        return "I";
    }
}
