package edu.rosehulman.minijavac.generator;

import java.nio.ByteBuffer;

public class IntegerEntry extends ConstantPoolEntry {
    static final byte TAG = 3;

    final int value;


    IntegerEntry(short index, int value) {
        super(index, TAG);
        this.value = value;
    }

    @Override
    byte[] getBytes() {
        return ByteBuffer.allocate(5)
            .put(TAG)
            .putInt(value)
            .array();
    }
}
