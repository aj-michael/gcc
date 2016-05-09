package edu.rosehulman.minijavac.typechecker;

import com.google.common.collect.ImmutableList;

import java.util.List;

public class DoubleType extends PrimitiveType {

    DoubleType() {
        super("double");
    }

    @Override
    public String getDescriptor() {
        return "D";
    }

    @Override
    public List<Byte> load(int index) {
        return getMemoryOperationBytes(index, (byte) 38, (byte) 24);
    }

    @Override
    public List<Byte> store(int index) {
        return getMemoryOperationBytes(index, (byte) 71, (byte) 57);
    }

    @Override
    public List<Byte> returnValue() {
        return ImmutableList.of((byte) 175);
    }
}
