package edu.rosehulman.minijavac.typechecker;

import java.util.HashMap;
import java.util.Map;

public class Type {

    public static final Type INT = new Type("int");
    public static final Type BOOLEAN = new Type("boolean");
    public static final Type DOUBLE = new Type("double");
    public static final Type FLOAT = new Type("float");
    public static final Type NULL = new Type("null");
    private static Map<String, Type> types = new HashMap<>();
    static {
        types.put("int", INT);
        types.put("boolean", BOOLEAN);
        types.put("double", DOUBLE);
        types.put("float", FLOAT);
        types.put("null", NULL);
    }

    public final String type;

    private Type(String type) {
        this.type = type;
    }

    public static Type of(String type) {
        if (type == null) {
            return NULL;
        } else if (!types.containsKey(type)) {
            types.put(type, new Type(type));
        }
        return types.get(type);
    }

    public boolean isA(Type otherType, Scope scope) {
        if (this == Type.NULL && !otherType.isPrimitiveType()) {
            return true;
        } else if (isPrimitiveType() || otherType.isPrimitiveType()) {
           return type.equals(otherType.type);
        } else if (type.equals("null")) {
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
                return new Type(parentScope.className).isA(otherType, scope);
            } else {
                return false;
            }
        }
    }

    public boolean isPrimitiveType() {
        return (this == INT) || (this == BOOLEAN);
    }

    @Override
    public String toString() {
        return type;
    }

    public String getDescriptor() {
        if (type.equals("int")) {
            return "I";
        } else if (type.equals("boolean")) {
            return "Z";
        } else if(type.equals("double")) {
            return "D";
        } else if(type.equals("float")) {
            return "F";
        } else if (type.equals("null")) {
            return "V";
        } else {
            return type;
        }
    }
}
