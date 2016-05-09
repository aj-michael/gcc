package edu.rosehulman.minijavac.typechecker;

import com.google.common.collect.ImmutableList;

import java.util.List;

public class FloatType extends PrimitiveType {

    FloatType() {
        super("float");
    }

    @Override
    public String getDescriptor() {
        return "F";
    }

    @Override
    public List<Byte> load(int index) {
        return getMemoryOperationBytes(index, (byte) 34, (byte) 23);
    }

    @Override
    public List<Byte> store(int index) {
        return getMemoryOperationBytes(index, (byte) 67, (byte) 56);
    }

    @Override
    public List<Byte> returnValue() {
        return ImmutableList.of((byte) 174);
    }
}
