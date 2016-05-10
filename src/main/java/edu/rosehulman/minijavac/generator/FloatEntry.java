package edu.rosehulman.minijavac.generator;

import java.nio.ByteBuffer;

public class FloatEntry extends ConstantPoolEntry {
    static final byte TAG = 4;

    final float value;


    FloatEntry(short index, float value) {
        super(index, TAG);
        this.value = value;
    }

    @Override
    byte[] getBytes() {
        return ByteBuffer.allocate(5)
                .put(TAG)
                .putFloat(value)
                .array();
    }
}
