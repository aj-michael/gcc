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

    @Override
    public List<Byte> plus() {
        return ImmutableList.of((byte) 99);
    }

    @Override
    public boolean isPlusSupported() {
        return true;
    }

    @Override
    public List<Byte> minus() {
        return ImmutableList.of((byte) 103);
    }

    @Override
    public boolean isMinusSupported() {
        return true;
    }

    @Override
    public List<Byte> multiply() {
        return ImmutableList.of((byte) 107);
    }

    @Override
    public boolean isMultiplySupported() {
        return true;
    }

    @Override
    public List<Byte> divide() {
        return ImmutableList.of((byte) 111);
    }

    @Override
    public boolean isDivideSupported() {
        return true;
    }

    @Override
    public List<Byte> equalsEquals() {
        //TODO
        return null;
    }

    @Override
    public boolean isEqualsEqualsSupported() {
        return true;
    }

    @Override
    public List<Byte> notEquals() {
        //TODO
        return null;
    }

    @Override
    public boolean isNotEqualsSupported() {
        return true;
    }

    @Override
    public List<Byte> greaterThan() {
        //TODO
        return null;
    }

    @Override
    public boolean isGreaterThanSupported() {
        return true;
    }

    @Override
    public List<Byte> greaterThanOrEqualTo() {
        //TODO
        return null;
    }

    @Override
    public boolean isGreaterThanOrEqualToSupported() {
        return true;
    }

    @Override
    public List<Byte> lessThan() {
        //TODO
        return null;
    }

    @Override
    public boolean isLessThanSupported() {
        return true;
    }

    @Override
    public List<Byte> lessThanOrEqualTo() {
        //TODO
        return null;
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
