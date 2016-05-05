package edu.rosehulman.minijavac.typechecker;

import com.google.common.collect.ImmutableList;
import edu.rosehulman.minijavac.ast.ClassDeclaration;
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
    public ClassDeclaration classDeclaration;

    public Scope(Program program) {
        this(program, Optional.empty(), null, false);
        classes.put("I", null);
        classes.put("int", null);
        classes.put("Z", null);
        classes.put("boolean", null);
        classes.put("null", null);
        classes.put("V", null);
        classes.put("Thread", new ThreadScope(this));
    }

    static class ThreadScope extends Scope {
        public ThreadScope(Scope scope) {
            super(scope, "java/lang/Thread");
            methods.put("start", new MethodDeclaration("start", ImmutableList.of()));
            methods.put("run", new MethodDeclaration("run", ImmutableList.of()));
            methods.put("join", new MethodDeclaration("join", ImmutableList.of()));
        }
    }

    public Scope(Scope parent) {
        this(parent.program, Optional.of(parent), parent.className, false);
    }

    public Scope(Scope parent, String className) {
        this(parent.program, Optional.of(parent), className, true);
    }

    public Scope(Scope parent, ClassDeclaration cd) {
        this(parent, cd.name);
        this.classDeclaration = cd;
    }

    public Optional<ClassDeclaration> getParent() {
        if (parent.isPresent() && parent.get().classDeclaration != null) {
            return Optional.of(parent.get().classDeclaration);
        } else {
            return Optional.empty();
        }
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
