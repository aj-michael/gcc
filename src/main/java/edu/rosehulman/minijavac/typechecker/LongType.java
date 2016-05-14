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

    @Override
    public List<Byte> plus() {
        return ImmutableList.of((byte) 97);
    }

    @Override
    public boolean isPlusSupported() {
        return true;
    }

    @Override
    public List<Byte> minus() {
        return ImmutableList.of((byte) 101);
    }

    @Override
    public boolean isMinusSupported() {
        return true;
    }

    @Override
    public List<Byte> multiply() {
        return ImmutableList.of((byte) 105);
    }

    @Override
    public boolean isMultiplySupported() {
        return true;
    }

    @Override
    public List<Byte> divide() {
        return ImmutableList.of((byte) 109);
    }

    @Override
    public boolean isDivideSupported() {
        return true;
    }

    @Override
    public List<Byte> equalsEquals() {
        return ImmutableList.of((byte) 148, (byte) 153, (byte) 0, (byte) 7, (byte) 3, (byte) 167, (byte) 0, (byte) 4, (byte) 4);
    }

    @Override
    public boolean isEqualsEqualsSupported() {
        return true;
    }

    @Override
    public List<Byte> notEquals() {
        return ImmutableList.of((byte) 148, (byte) 153, (byte) 0, (byte) 7, (byte) 4, (byte) 167, (byte) 0, (byte) 4, (byte) 3);
    }

    @Override
    public boolean isNotEqualsSupported() {
        return true;
    }

    @Override
    public List<Byte> greaterThan() {
        return ImmutableList.of((byte) 148, (byte) 157, (byte) 0, (byte) 7, (byte) 3, (byte) 167, (byte) 0, (byte) 4, (byte) 4);
    }

    @Override
    public boolean isGreaterThanSupported() {
        return true;
    }

    @Override
    public List<Byte> greaterThanOrEqualTo() {
        return ImmutableList.of((byte) 148, (byte) 156, (byte) 0, (byte) 7, (byte) 3, (byte) 167, (byte) 0, (byte) 4, (byte) 4);
    }

    @Override
    public boolean isGreaterThanOrEqualToSupported() {
        return true;
    }

    @Override
    public List<Byte> lessThan() {
        return ImmutableList.of((byte) 148, (byte) 155, (byte) 0, (byte) 7, (byte) 3, (byte) 167, (byte) 0, (byte) 4, (byte) 4);
    }

    @Override
    public boolean isLessThanSupported() {
        return true;
    }

    @Override
    public List<Byte> lessThanOrEqualTo() {
        return ImmutableList.of((byte) 148, (byte) 158, (byte) 0, (byte) 7, (byte) 3, (byte) 167, (byte) 0, (byte) 4, (byte) 4);
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
