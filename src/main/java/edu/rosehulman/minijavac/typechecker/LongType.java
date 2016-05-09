package edu.rosehulman.minijavac.typechecker;

import com.google.common.collect.ImmutableList;

import java.util.List;

public class LongType extends PrimitiveType {

    LongType() {
        super("long");
    }

    @Override
    public String getDescriptor() {
        return "J";
    }

    @Override
    public List<Byte> load(int index) {
        return getMemoryOperationBytes(index, (byte) 30, (byte) 22);
    }

    @Override
    public List<Byte> store(int index) {
        return getMemoryOperationBytes(index, (byte) 63, (byte) 55);
    }

    @Override
    public List<Byte> returnValue() {
        return ImmutableList.of((byte) 173);
    }
}
