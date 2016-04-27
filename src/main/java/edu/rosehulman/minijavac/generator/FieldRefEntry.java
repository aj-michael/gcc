package edu.rosehulman.minijavac.generator;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

public class FieldRefEntry extends ConstantPoolEntry {
    static final byte TAG = 9;
    final ClassEntry classEntry;
    final NameAndTypeEntry nameAndTypeEntry;

    FieldRefEntry(short index, ClassEntry classEntry, NameAndTypeEntry nameAndTypeEntry) {
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
