package edu.rosehulman.minijavac.generator;

import java.nio.ByteBuffer;

public class NameAndTypeEntry extends ConstantPoolEntry {
    static final byte TAG = 12;
    final Utf8Entry nameEntry;
    final Utf8Entry descriptorEntry;

    public NameAndTypeEntry(short index, Utf8Entry nameEntry, Utf8Entry descriptorEntry) {
        super(index, TAG);
        this.nameEntry = nameEntry;
        this.descriptorEntry = descriptorEntry;
    }

    @Override
    byte[] getBytes() {
        return ByteBuffer.allocate(5)
                .put(TAG)
                .putShort(nameEntry.index)
                .putShort(descriptorEntry.index)
                .array();
    }
}
