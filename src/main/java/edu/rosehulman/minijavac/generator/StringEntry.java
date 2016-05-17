package edu.rosehulman.minijavac.generator;

import java.nio.ByteBuffer;

public class StringEntry extends ConstantPoolEntry {
    static final byte TAG = 8;
    final short stringIndex;

    StringEntry(short index, short stringIndex) {
        super(index, TAG);
        this.stringIndex = stringIndex;
    }

    @Override
    byte[] getBytes() {
        return ByteBuffer.allocate(3)
            .put(TAG)
            .putShort(stringIndex)
            .array();
    }
}
