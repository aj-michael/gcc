package edu.rosehulman.minijavac.typechecker;

import edu.rosehulman.minijavac.ast.MethodDeclaration;
import edu.rosehulman.minijavac.ast.Program;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class Scope {
    public final Optional<Scope> parent;
    public final Map<String, Type> declaredVariables = new HashMap<>();
    public final Map<String, Scope> classes = new HashMap<>();
    public final Map<String, MethodDeclaration> methods = new LinkedHashMap<>();
    public final Program program;
    public final String className;
    public final boolean isClass;

    public Scope(Program program) {
        this(program, Optional.empty(), null, false);
        classes.put("int", null);
        classes.put("boolean", null);
    }

    public Scope(Scope parent) {
        this(parent.program, Optional.of(parent), parent.className, false);
    }

    public Scope(Scope parent, String className) {
        this(parent.program, Optional.of(parent), className, true);
    }

    private Scope(Program program, Optional<Scope> parent, String className, boolean isClass) {
        this.program = program;
        this.parent = parent;
        this.className = className;
        this.isClass = isClass;
    }

    public boolean containsLocalVariable(String name) {
        if (isClass) {
            return false;
        } else if (declaredVariables.containsKey(name)) {
            return true;
        } else if (parent.isPresent()) {
            return parent.get().containsLocalVariable(name);
        } else {
            return false;
        }
    }

    public boolean containsVariable(String name) {
        if (declaredVariables.containsKey(name)) {
            return true;
        } else if (parent.isPresent()) {
            return parent.get().containsVariable(name);
        } else {
            return false;
        }
    }

    public Type getVariableType(String name) {
        if (declaredVariables.containsKey(name)) {
            return declaredVariables.get(name);
        } else if (parent.isPresent()) {
            return parent.get().getVariableType(name);
        } else {
            return null;
        }
    }

    public boolean containsMethod(String name) {
        if (methods.containsKey(name)) {
            return true;
        } else if (parent.isPresent()) {
            return parent.get().containsMethod(name);
        } else {
            return false;
        }
    }

    public boolean containsClass(String name) {
        if (classes.containsKey(name)) {
            return true;
        } else if (parent.isPresent()) {
            return parent.get().containsClass(name);
        } else {
            return false;
        }
    }

    public MethodDeclaration getMethod(String name) {
        if (methods.containsKey(name)) {
            return methods.get(name);
        } else if (parent.isPresent()) {
            return parent.get().getMethod(name);
        } else {
            return null;
        }
    }

    public Scope getClassScope(String type) {
        if (classes.containsKey(type)) {
            return classes.get(type);
        } else if (parent.isPresent()) {
            return parent.get().getClassScope(type);
        } else {
            return null;
        }
    }
}