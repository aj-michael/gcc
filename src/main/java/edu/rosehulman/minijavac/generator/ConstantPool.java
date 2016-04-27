package edu.rosehulman.minijavac.generator;

import java.util.ArrayList;
import java.util.List;

public class ConstantPool {
    private short index;
    List<ConstantPoolEntry> entries;

    public ConstantPool() {
        index = 1;
        entries = new ArrayList<>();
    }


    public Utf8Entry newUtf8Entry(String string) {
        Utf8Entry entry = new Utf8Entry(string, index++);
        entries.add(entry);
        return entry;
    }


}
