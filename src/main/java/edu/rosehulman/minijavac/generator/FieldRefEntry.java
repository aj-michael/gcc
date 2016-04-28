package edu.rosehulman.minijavac.generator;

import java.nio.ByteBuffer;

public class FieldRefEntry extends ConstantPoolEntry {
    static final byte TAG = 9;
    final short classEntryIndex;
    final NameAndTypeEntry nameAndTypeEntry;

    FieldRefEntry(short index, short classEntryIndex, NameAndTypeEntry nameAndTypeEntry) {
        super(index, TAG);
        this.classEntryIndex = classEntryIndex;
        this.nameAndTypeEntry = nameAndTypeEntry;
    }

    @Override
    byte[] getBytes() {
        return ByteBuffer.allocate(5)
                .put(TAG)
                .putShort(classEntryIndex)
                .putShort(nameAndTypeEntry.index)
                .array();
    }
}
