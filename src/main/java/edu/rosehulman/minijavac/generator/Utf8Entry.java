package edu.rosehulman.minijavac.generator;

import java.nio.ByteBuffer;

public class Utf8Entry extends ConstantPoolEntry {
    static final byte TAG = 1;

    final short length;
    final byte[] bytes;

    Utf8Entry(short index, String string) {
        super(index, TAG);
        length = (short) string.length();
        bytes = string.getBytes();
    }

    @Override
    public byte[] getBytes() {
        return ByteBuffer.allocate(3 + length)
                .put(TAG)
                .putShort(length)
                .put(bytes)
                .array();
    }
}
