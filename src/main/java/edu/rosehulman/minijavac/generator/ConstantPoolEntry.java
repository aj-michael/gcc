package edu.rosehulman.minijavac.generator;

abstract class ConstantPoolEntry {
    public final short index;
    public final byte tag;

    ConstantPoolEntry(short index, byte tag) {
        this.index = index;
        this.tag = tag;
    }

    abstract byte[] getBytes();
}
