package edu.rosehulman.minijavac.typechecker;

import com.google.common.collect.ImmutableList;

import java.util.List;

public class IntType extends PrimitiveType {

    IntType() {
        super("int");
    }

    @Override
    public String getDescriptor() {
        return "I";
    }

    @Override
    public List<Byte> load(int index) {
        return getMemoryOperationBytes(index, (byte) 26, (byte) 21);
    }

    @Override
    public List<Byte> store(int index) {
        return getMemoryOperationBytes(index, (byte) 59, (byte) 54);
    }

    @Override
    public List<Byte> returnValue() {
        return ImmutableList.of((byte) 172);
    }
}
