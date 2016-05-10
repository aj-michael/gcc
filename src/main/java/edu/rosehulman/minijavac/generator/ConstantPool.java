package edu.rosehulman.minijavac.generator;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import edu.rosehulman.minijavac.ast.ClassDeclaration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConstantPool {
    private short index = 1;
    public List<ConstantPoolEntry> entries = new ArrayList<>();
    Map<String, Utf8Entry> utf8EntryMap = new HashMap<>();
    Map<String, ClassEntry> classEntryMap = new HashMap<>();
    Table<String, String, NameAndTypeEntry> nameAndTypeEntryTable = HashBasedTable.create();
    Table<ClassEntry, NameAndTypeEntry, MethodRefEntry> methodRefEntryTable = HashBasedTable.create();
    Map<NameAndTypeEntry, FieldRefEntry> fieldRefEntryMap = new HashMap<>();
    Map<Integer, IntegerEntry> integerEntryMap = new HashMap<>();
    Map<Double, DoubleEntry> doubleEntryMap = new HashMap<>();
    Map<Float, FloatEntry> floatEntryMap = new HashMap<>();
    Map<Long, LongEntry> longEntryMap = new HashMap<>();
    public Map<String, FieldRefEntry> thisFieldRefEntryMap = new HashMap<>();

    public final Utf8Entry codeEntry;
    final Utf8Entry constructorNameEntry;
    final Utf8Entry constructorDescriptorEntry;
    public final FieldRefEntry systemOutEntry;
    public final MethodRefEntry printlnEntry;
    final MethodRefEntry objectConstructorEntry;

    public ConstantPool() {
        codeEntry = utf8Entry("Code");
        systemOutEntry = fieldRefEntry("java/lang/System", "out", "Ljava/io/PrintStream;");
        constructorNameEntry = utf8Entry("<init>");
        constructorDescriptorEntry = utf8Entry("()V");
        printlnEntry = methodRefEntry("java/io/PrintStream", "println", "(I)V");
        objectConstructorEntry = methodRefEntry("java/lang/Object", "<init>", "()V");
    }

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

    public ClassEntry classEntry(ClassDeclaration cd) {
        if (classEntryMap.containsKey(cd.name)) {
            return classEntryMap.get(cd.name);
        } else {
            Utf8Entry nameEntry = utf8Entry(cd.name);
            ClassEntry classEntry = new ClassEntry(index++, nameEntry);
            entries.add(classEntry);
            classEntryMap.put(cd.name, classEntry);
            classEntry(cd.getParentClass());
            return classEntry;
        }
    }

    public IntegerEntry integerEntry(int value) {
        if (integerEntryMap.containsKey(value)) {
            return integerEntryMap.get(value);
        } else {
            IntegerEntry entry = new IntegerEntry(index++, value);
            entries.add(entry);
            integerEntryMap.put(value, entry);
            return entry;
        }
    }

    public DoubleEntry doubleEntry(double value) {
        if (doubleEntryMap.containsKey(value)) {
            return doubleEntryMap.get(value);
        } else {
            DoubleEntry entry = new DoubleEntry(index++, value);
            entries.add(entry);
            doubleEntryMap.put(value, entry);
            return entry;
        }
    }

    public FloatEntry floatEntry(float value) {
        if (floatEntryMap.containsKey(value)) {
            return floatEntryMap.get(value);
        } else {
            FloatEntry entry = new FloatEntry(index++, value);
            entries.add(entry);
            floatEntryMap.put(value, entry);
            return entry;
        }
    }

    public LongEntry longEntry(long value) {
        if (longEntryMap.containsKey(value)) {
            return longEntryMap.get(value);
        } else {
            LongEntry entry = new LongEntry(index++, value);
            entries.add(entry);
            longEntryMap.put(value, entry);
            return entry;
        }
    }

    public ClassEntry classEntry(String classDescriptor) {
        if (classEntryMap.containsKey(classDescriptor)) {
            return classEntryMap.get(classDescriptor);
        } else {
            Utf8Entry nameEntry = utf8Entry(classDescriptor);
            ClassEntry classEntry = new ClassEntry(index++, nameEntry);
            entries.add(classEntry);
            classEntryMap.put(classDescriptor, classEntry);
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

    public FieldRefEntry fieldRefEntry(String className, String fieldName, String fieldDescriptor) {
        NameAndTypeEntry nameAndTypeEntry = nameAndTypeEntry(fieldName, fieldDescriptor);
        ClassEntry classEntry = classEntry(className);
        if (fieldRefEntryMap.containsKey(nameAndTypeEntry)) {
            return fieldRefEntryMap.get(nameAndTypeEntry);
        } else {
            FieldRefEntry entry = new FieldRefEntry(index++, classEntry.index, nameAndTypeEntry);
            entries.add(entry);
            fieldRefEntryMap.put(nameAndTypeEntry, entry);
            return entry;
        }
    }

    public MethodRefEntry methodRefEntry(String className, String methodName, String methodDescriptor) {
        ClassEntry classEntry = classEntry(className);
        NameAndTypeEntry nameAndTypeEntry = nameAndTypeEntry(methodName, methodDescriptor);
        if (methodRefEntryTable.contains(classEntry, nameAndTypeEntry)) {
            return methodRefEntryTable.get(classEntry, nameAndTypeEntry);
        } else {
            MethodRefEntry entry = new MethodRefEntry(index++, classEntry, nameAndTypeEntry);
            entries.add(entry);
            methodRefEntryTable.put(classEntry, nameAndTypeEntry, entry);
            return entry;
        }
    }

    public void thisFieldRefEntry(String className, String fieldName, String fieldDescriptor) {
        FieldRefEntry fieldEntry = fieldRefEntry(className, fieldName, fieldDescriptor);
        thisFieldRefEntryMap.put(fieldName, fieldEntry);
    }
}
