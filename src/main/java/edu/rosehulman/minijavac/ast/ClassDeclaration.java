package edu.rosehulman.minijavac.ast;

import edu.rosehulman.minijavac.typechecker.Scope;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClassDeclaration {
    public final String name;
    public final Optional<String> parentClassName;
    public final List<VariableDeclaration> classVariableDeclarations;
    public final List<MethodDeclaration> methodDeclarations;
    public ClassDeclaration parent;

    public ClassDeclaration(String name, Optional<String> parentClassName, List<VariableDeclaration> classVariableDeclarations,
            List<MethodDeclaration> methodDeclarations) {
        this.name = name;
        this.parentClassName = parentClassName;
        this.classVariableDeclarations = classVariableDeclarations;
        this.methodDeclarations = methodDeclarations;
    }

    public void setParent(ClassDeclaration parent) {
        this.parent = parent;
    }

    public List<VariableDeclaration> getClassVariables() {
        if (parent == null) {
            return new ArrayList<>(classVariableDeclarations);
        } else {
            List<VariableDeclaration> parentVariables = parent.getClassVariables();
            parentVariables.addAll(classVariableDeclarations);
            return parentVariables;
        }
    }

    public List<String> typecheck(Scope scope) {
        List<String> errors = new ArrayList<>();
        for (VariableDeclaration cvd : classVariableDeclarations) {
            if (scope.containsVariable(cvd.name)) {
                errors.add("The class variable " + cvd.name + " is already declared.  Redeclaration and shadowing are not allowed.");
            } else if (!scope.containsClass(cvd.type)) {
                errors.add("Cannot find class named " + cvd.type);
            } else {
                scope.declaredVariables.put(cvd.name, cvd.type);
            }
        }
        for (MethodDeclaration md : methodDeclarations) {
            if (scope.methods.containsKey(md.name)) {
                errors.add("Cannot redeclare method " + md.name);
            } else if (scope.containsMethod(md.name) && !md.canOverride(scope.getMethod(md.name))) {
                errors.add("Cannot overload methods.  Method " + md.name + " has different type signature than inherited method of the same name.");
            } else {
                scope.methods.put(md.name, md);
            }
        }
        return errors;
    }

    public List<String> typecheckAgain(Scope scope) {
        List<String> errors = new ArrayList<>();
        System.out.println(scope.methods == null);
        System.out.println(scope.methods.values());
        for (MethodDeclaration md : scope.methods.values()) {
            errors.addAll(md.typecheck(new Scope(scope)));
        }
        return errors;
    }
}
