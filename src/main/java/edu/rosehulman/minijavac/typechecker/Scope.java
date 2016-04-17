package edu.rosehulman.minijavac.typechecker;

import edu.rosehulman.minijavac.ast.ClassDeclaration;
import edu.rosehulman.minijavac.ast.MethodDeclaration;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Scope {
    public final Optional<Scope> parent;
    public final Map<String, String>declaredVariables = new HashMap<>();
    public final Map<String, Scope> classes = new HashMap<>();
    public final Map<String, MethodDeclaration> methods = new HashMap<>();

    public Scope() {
        this.parent = Optional.empty();
        classes.put("int", null);
        classes.put("boolean", null);
    }

    public Scope(Scope parent) {
        this.parent = Optional.of(parent);
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

    public String getVariableType(String name) {
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

    //public Map<String, ClassDeclaration> getDeclaredClasses() {
    //    return parent.isPresent() ? parent.get().getDeclaredClasses() : classes;
    //}

    /*public boolean addVariable(String name, String type) {
        if (containsVariable(name)) {
            return false;
        } else {
            declaredVariables.put(name, type);
            return true;
        }
    }*/
}
