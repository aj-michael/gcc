package edu.rosehulman.minijavac.typechecker;

import com.google.common.collect.ImmutableList;

import java.util.List;

public class BooleanType extends PrimitiveType {

    BooleanType() {
        super("boolean");
    }

    @Override
    public String getDescriptor() {
        return "Z";
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

    @Override
    public List<Byte> plus() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isPlusSupported() {
        return false;
    }

    @Override
    public List<Byte> minus() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isMinusSupported() {
        return false;
    }

    @Override
    public List<Byte> multiply() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isMultiplySupported() {
        return false;
    }

    @Override
    public List<Byte> divide() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isDivideSupported() {
        return false;
    }

    @Override
    public List<Byte> equalsEquals() {
        return ImmutableList.of((byte) 160, (byte) 0, (byte) 7, (byte) 4, (byte) 167, (byte) 0, (byte) 4, (byte) 3);
    }

    @Override
    public boolean isEqualsEqualsSupported() {
        return true;
    }

    @Override
    public List<Byte> notEquals() {
        return ImmutableList.of((byte) 159, (byte) 0, (byte) 7, (byte) 4, (byte) 167, (byte) 0, (byte) 4, (byte) 3);
    }

    @Override
    public boolean isNotEqualsSupported() {
        return true;
    }

    @Override
    public List<Byte> greaterThan() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isGreaterThanSupported() {
        return false;
    }

    @Override
    public List<Byte> greaterThanOrEqualTo() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isGreaterThanOrEqualToSupported() {
        return false;
    }

    @Override
    public List<Byte> lessThan() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isLessThanSupported() {
        return false;
    }

    @Override
    public List<Byte> lessThanOrEqualTo() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isLessThanOrEqualToSupported() {
        return false;
    }

    @Override
    public List<Byte> and() {
        return ImmutableList.of((byte) 126);
    }

    @Override
    public boolean isAndSupported() {
        return true;
    }

    @Override
    public List<Byte> or() {
        return ImmutableList.of((byte) 128);
    }

    @Override
    public boolean isOrSupported() {
        return true;
    }
}
