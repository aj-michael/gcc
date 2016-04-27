package edu.rosehulman.minijavac.generator;

abstract class ConstantPoolEntry {
    final short index;
    final byte tag;

    ConstantPoolEntry(short index, byte tag) {
        this.index = index;
        this.tag = tag;
    }

    abstract byte[] getBytes();
}
