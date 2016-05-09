package edu.rosehulman.minijavac.typechecker;

import com.google.common.collect.ImmutableList;

import java.util.List;

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

    @Override
    public List<Byte> load(int index) {
        return getMemoryOperationBytes(index, (byte) 42, (byte) 25);
    }

    @Override
    public List<Byte> store(int index) {
        return getMemoryOperationBytes(index, (byte) 75, (byte) 58);
    }

    @Override
    public List<Byte> returnValue() {
        return ImmutableList.of((byte) 176);
    }
}
