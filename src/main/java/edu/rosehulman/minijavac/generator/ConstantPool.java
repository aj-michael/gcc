package edu.rosehulman.minijavac.generator;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import edu.rosehulman.minijavac.ast.MethodDeclaration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConstantPool {
    private short index = 1;
    List<ConstantPoolEntry> entries = new ArrayList<>();
    Map<String, Utf8Entry> utf8EntryMap = new HashMap<>();
    Map<String, ClassEntry> classEntryMap = new HashMap<>();
    Table<String, String, NameAndTypeEntry> nameAndTypeEntryTable = HashBasedTable.create();
    Map<NameAndTypeEntry, MethodRefEntry> methodRefEntryMap = new HashMap<>();

    public Utf8Entry utf8Entry(String string) {
        if (utf8EntryMap.containsKey(string)) {
            return utf8EntryMap.get(string);
        } else {
            Utf8Entry entry = new Utf8Entry(index++, string);
            entries.add(entry);
            utf8EntryMap.put(string, entry);
            return entry;
        }
    }

    public ClassEntry classEntry(String className) {
        if (classEntryMap.containsKey(className)) {
            return classEntryMap.get(className);
        } else {
            Utf8Entry nameEntry = utf8Entry(className);
            ClassEntry classEntry = new ClassEntry(index++, nameEntry);
            entries.add(classEntry);
            classEntryMap.put(className, classEntry);
            return classEntry;
        }
    }

    public NameAndTypeEntry nameAndTypeEntry(String name, String descriptor) {
        if (nameAndTypeEntryTable.contains(name, descriptor)) {
            return nameAndTypeEntryTable.get(name, descriptor);
        } else {
            Utf8Entry nameEntry = utf8Entry(name);
            Utf8Entry descriptorEntry = utf8Entry(descriptor);
            NameAndTypeEntry entry = new NameAndTypeEntry(index++, nameEntry, descriptorEntry);
            entries.add(entry);
            nameAndTypeEntryTable.put(name, descriptor, entry);
            return entry;
        }
    }

    public MethodRefEntry methodRefEntry(ClassEntry classEntry, MethodDeclaration md) {
        NameAndTypeEntry nameAndTypeEntry = nameAndTypeEntry(md.name, md.getDescriptor());
        if (methodRefEntryMap.containsKey(nameAndTypeEntry)) {
            return methodRefEntryMap.get(nameAndTypeEntry);
        } else {
            MethodRefEntry entry = new MethodRefEntry(index++, classEntry, nameAndTypeEntry);
            entries.add(entry);
            methodRefEntryMap.put(nameAndTypeEntry, entry);
            return entry;
        }
    }
}
