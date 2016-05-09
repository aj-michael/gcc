package edu.rosehulman.minijavac.typechecker;

import com.google.common.collect.ImmutableList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Type {

    public static final BooleanType BOOLEAN = new BooleanType();
    public static final IntType INT = new IntType();
    public static final DoubleType DOUBLE = new DoubleType();
    public static final FloatType FLOAT = new FloatType();
    public static final LongType LONG = new LongType();
    public static final NullType NULL = new NullType();

    private static Map<String, Type> types = new HashMap<>();

    static {
        types.put("boolean", BOOLEAN);
        types.put("int", INT);
        types.put("double", DOUBLE);
        types.put("float", FLOAT);
        types.put("long", LONG);
        types.put("null", NULL);
    }

    public final String type;

    Type(String type) {
        this.type = type;
    }

    public static Type of(String type) {
        if (type == null) {
            return Type.NULL;
        } else if (!types.containsKey(type)) {
            types.put(type, new ReferenceType(type));
        }
        return types.get(type);
    }

    public boolean isA(Type otherType, Scope scope) {
        if (this == Type.NULL && !otherType.isPrimitiveType()) {
            return true;
        } else if (isPrimitiveType() || otherType.isPrimitiveType()) {
           return type.equals(otherType.type);
        } else if (this == NullType.NULL) {
            return true;
        } else if (type.equals(otherType.type)) {
            return true;
        } else {
            Scope classScope = scope.getClassScope(type);
            if (classScope == null) {
               return false;
            }

            Scope parentScope = classScope.parent.get();
            if (parentScope.parent.isPresent()) {
                return new ReferenceType(parentScope.className).isA(otherType, scope);
            } else {
                return false;
            }
        }
    }

    @Override
    public String toString() {
        return type;
    }

    List<Byte> getMemoryOperationBytes(int index, byte nByte, byte longByte) {
        if(index < 0) {
            throw new IndexOutOfBoundsException("Index less than zero is no allowed");
        } else if(index <= 3) {
            return ImmutableList.of((byte) (nByte + index));
        } else {
            return ImmutableList.of(longByte, (byte) index);
        }
    }

    public abstract boolean isPrimitiveType();
    public abstract String getDescriptor();

    public abstract List<Byte> load(int index);
    public abstract List<Byte> store(int index);
    public abstract List<Byte> returnValue();
}
