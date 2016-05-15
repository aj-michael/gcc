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

    @Override
    public List<Byte> plus() {
        return ImmutableList.of((byte) 98);
    }

    @Override
    public boolean isPlusSupported() {
        return true;
    }

    @Override
    public List<Byte> minus() {
        return ImmutableList.of((byte) 102);
    }

    @Override
    public boolean isMinusSupported() {
        return true;
    }

    @Override
    public List<Byte> multiply() {
        return ImmutableList.of((byte) 106);
    }

    @Override
    public boolean isMultiplySupported() {
        return true;
    }

    @Override
    public List<Byte> divide() {
        return ImmutableList.of((byte) 110);
    }

    @Override
    public boolean isDivideSupported() {
        return true;
    }

    @Override
    public List<Byte> equalsEquals() {
        return ImmutableList.of((byte) 150, (byte) 153, (byte) 0, (byte) 7, (byte) 3, (byte) 167, (byte) 0, (byte) 4, (byte) 4);
    }

    @Override
    public boolean isEqualsEqualsSupported() {
        return true;
    }

    @Override
    public List<Byte> notEquals() {
        return ImmutableList.of((byte) 150, (byte) 153, (byte) 0, (byte) 7, (byte) 4, (byte) 167, (byte) 0, (byte) 4, (byte) 3);
    }

    @Override
    public boolean isNotEqualsSupported() {
        return true;
    }

    @Override
    public List<Byte> greaterThan() {
        return ImmutableList.of((byte) 150, (byte) 157, (byte) 0, (byte) 7, (byte) 3, (byte) 167, (byte) 0, (byte) 4, (byte) 4);
    }

    @Override
    public boolean isGreaterThanSupported() {
        return true;
    }

    @Override
    public List<Byte> greaterThanOrEqualTo() {
        return ImmutableList.of((byte) 150, (byte) 156, (byte) 0, (byte) 7, (byte) 3, (byte) 167, (byte) 0, (byte) 4, (byte) 4);
    }

    @Override
    public boolean isGreaterThanOrEqualToSupported() {
        return true;
    }

    @Override
    public List<Byte> lessThan() {
        return ImmutableList.of((byte) 150, (byte) 155, (byte) 0, (byte) 7, (byte) 3, (byte) 167, (byte) 0, (byte) 4, (byte) 4);
    }

    @Override
    public boolean isLessThanSupported() {
        return true;
    }

    @Override
    public List<Byte> lessThanOrEqualTo() {
        return ImmutableList.of((byte) 150, (byte) 158, (byte) 0, (byte) 7, (byte) 3, (byte) 167, (byte) 0, (byte) 4, (byte) 4);
    }

    @Override
    public boolean isLessThanOrEqualToSupported() {
        return true;
    }

    @Override
    public List<Byte> and() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isAndSupported() {
        return false;
    }

    @Override
    public List<Byte> or() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isOrSupported() {
        return false;
    }
}
