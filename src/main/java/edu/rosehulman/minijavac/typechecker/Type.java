package edu.rosehulman.minijavac.typechecker;

import java.util.HashMap;
import java.util.Map;

public abstract class Type {

    public static final BooleanType BOOLEAN = new BooleanType();
    public static final IntType INT = new IntType();
    public static final NullType NULL = new NullType();

    private static Map<String, Type> types = new HashMap<>();

    static {
        types.put("boolean", BOOLEAN);
        types.put("int", INT);
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


    public abstract boolean isPrimitiveType();
    public abstract String getDescriptor();
}
