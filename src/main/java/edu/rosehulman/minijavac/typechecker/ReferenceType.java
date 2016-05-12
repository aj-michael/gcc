package edu.rosehulman.minijavac.typechecker;

import com.google.common.collect.ImmutableList;

import java.util.List;

public class ReferenceType extends Type {

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
        return ImmutableList.of((byte) 166, (byte) 0, (byte) 7, (byte) 4, (byte) 167, (byte) 0, (byte) 4, (byte) 3);
    }

    @Override
    public boolean isEqualsEqualsSupported() {
        return true;
    }

    @Override
    public List<Byte> notEquals() {
        return ImmutableList.of((byte) 165, (byte) 0, (byte) 7, (byte) 4, (byte) 167, (byte) 0, (byte) 4, (byte) 3);
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
