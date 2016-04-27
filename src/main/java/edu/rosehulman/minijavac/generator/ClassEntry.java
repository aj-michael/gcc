package edu.rosehulman.minijavac.generator;

import java.nio.ByteBuffer;

public class ClassEntry extends ConstantPoolEntry {
    static final byte TAG = 7;

    final Utf8Entry nameEntry;

    public ClassEntry(short index, Utf8Entry nameEntry) {
        super(index, TAG);
        this.nameEntry = nameEntry;
    }

    @Override
    public byte[] getBytes() {
        return ByteBuffer.allocate(3)
                .put(TAG)
                .putShort(nameEntry.index)
                .array();
    }
}
