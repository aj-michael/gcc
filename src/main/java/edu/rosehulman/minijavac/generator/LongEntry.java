package edu.rosehulman.minijavac.generator;

import java.nio.ByteBuffer;

public class LongEntry extends ConstantPoolEntry {
    static final byte TAG = 5;

    final long value;


    LongEntry(short index, long value) {
        super(index, TAG);
        this.value = value;
    }

    @Override
    byte[] getBytes() {
        return ByteBuffer.allocate(9)
                .put(TAG)
                .putLong(value)
                .array();
    }
}
