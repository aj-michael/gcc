package edu.rosehulman.minijavac.typechecker;

public class Type {

    public static final Type INT = new Type("int");
    public static final Type BOOLEAN = new Type("boolean");
    public static final Type NULL = new Type("null");

    public final String type;

    public Type(String type) {
        this.type = type;
    }

    public boolean isA(Type otherType, Scope scope) {
        if (isPrimitiveType() || otherType.isPrimitiveType()) {
           return type.equals(otherType.type);
        }

        if (type.equals("null")) {
            return true;
        }

        if (type.equals(otherType.type)) {
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
        return type.equals("int") || type.equals("boolean");
    }

    @Override
    public String toString() {
        return type;
    }
}
