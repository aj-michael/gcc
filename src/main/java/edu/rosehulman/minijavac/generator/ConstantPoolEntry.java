package edu.rosehulman.minijavac.generator;

public interface ConstantPoolEntry {
    byte getTag();
    byte[] getBytes();
    short getIndex();
}
