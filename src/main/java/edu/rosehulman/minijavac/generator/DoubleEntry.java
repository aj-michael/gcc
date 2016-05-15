package edu.rosehulman.minijavac.generator;

import java.nio.ByteBuffer;

public class DoubleEntry extends ConstantPoolEntry {
    static final byte TAG = 6;

    final double value;

    DoubleEntry(short index, double value) {
        super(index, TAG);
        this.value = value;
    }

    @Override
    byte[] getBytes() {
        return ByteBuffer.allocate(9)
                .put(TAG)
                .putDouble(value)
                .array();
    }
}
