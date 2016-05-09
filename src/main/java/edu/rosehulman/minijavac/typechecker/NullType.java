package edu.rosehulman.minijavac.typechecker;

import com.google.common.collect.ImmutableList;

import java.util.List;

public class NullType extends ReferenceType {

    NullType() {
        super("null");
    }

    @Override
    public String getDescriptor() {
        return "V";
    }

    @Override
    public List<Byte> returnValue() {
        return ImmutableList.of((byte) 177);
    }
}
