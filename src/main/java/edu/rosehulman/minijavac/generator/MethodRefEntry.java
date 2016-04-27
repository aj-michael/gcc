package edu.rosehulman.minijavac.generator;

import java.nio.ByteBuffer;

public class MethodRefEntry extends ConstantPoolEntry {
    static final byte TAG = 10;
    final ClassEntry classEntry;
    final NameAndTypeEntry nameAndTypeEntry;

    MethodRefEntry(short index, ClassEntry classEntry, NameAndTypeEntry nameAndTypeEntry) {
        super(index, TAG);
        this.classEntry = classEntry;
        this.nameAndTypeEntry = nameAndTypeEntry;
    }

    @Override
    byte[] getBytes() {
        return ByteBuffer.allocate(5)
                .put(TAG)
                .putShort(classEntry.index)
                .putShort(nameAndTypeEntry.index)
                .array();
    }
}
