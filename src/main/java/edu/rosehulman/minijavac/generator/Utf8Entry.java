package edu.rosehulman.minijavac.generator;

public class Utf8Entry implements ConstantPoolEntry {
    final short length;
    final byte[] bytes;
    final short index;

    Utf8Entry(String string, short index) {
        length = (short) string.length();
        bytes = string.getBytes();
        this.index = index;
    }

    @Override
    public byte getTag() {
        return 1;
    }

    @Override
    public byte[] getBytes() {
        return null;
    }

    @Override
    public short getIndex() {
        return index;
    }
}
