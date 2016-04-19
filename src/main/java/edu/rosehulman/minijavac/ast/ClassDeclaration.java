package edu.rosehulman.minijavac.ast;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import edu.rosehulman.minijavac.typechecker.Scope;
import edu.rosehulman.minijavac.typechecker.Type;

public class ClassDeclaration {
    public final String name;
    public final Optional<String> parentClassName;
    public final List<VariableDeclaration> classVariableDeclarations;
    public final List<MethodDeclaration> methodDeclarations;

    public ClassDeclaration(String name, Optional<String> parentClassName, List<VariableDeclaration> classVariableDeclarations,
            List<MethodDeclaration> methodDeclarations) {
        this.name = name;
        this.parentClassName = parentClassName;
        this.classVariableDeclarations = classVariableDeclarations;
        this.methodDeclarations = methodDeclarations;
    }

    public List<String> typecheck(Scope scope) {
        List<String> errors = new ArrayList<>();
        for (MethodDeclaration md : methodDeclarations) {
            if (scope.methods.containsKey(md.name)) {
                errors.add("Cannot redeclare method " + md.name);
                if (!md.canOverride(scope.getMethod(md.name))) {
                    errors.add("Cannot overload methods.  Method " + md.name + " has different type signature than inherited method of the same name.");
                }
            } else if (scope.containsMethod(md.name) && !md.canOverride(scope.getMethod(md.name))) {
                errors.add("Cannot overload methods.  Method " + md.name + " has different type signature than inherited method of the same name.");
            } else {
                scope.methods.put(md.name, md);
            }
        }

        return errors;
    }

    public List<String> typecheck15(Scope scope) {
        List<String> errors = new ArrayList<>();
        for(MethodDeclaration md : methodDeclarations) {
            if (!(md instanceof MainMethodDeclaration) && !scope.containsClass(md.returnType)) {
                System.out.println("$$$");
                errors.add("Cannot find class named " + md.returnType);
            }
        }
        return errors;
    }

    public List<String> typecheckAgain(Scope scope) {
        List<String> errors = new ArrayList<>();
        for (VariableDeclaration cvd : classVariableDeclarations) {
            if (scope.containsVariable(cvd.name)) {
                errors.add("The class variable " + cvd.name + " is already declared.  Redeclaration and shadowing are not allowed.");
            } else if (!scope.containsClass(cvd.type)) {
                errors.add("Cannot find class named " + cvd.type);
            } else {
                scope.declaredVariables.put(cvd.name, new Type(cvd.type));
            }
        }
        for (MethodDeclaration md : scope.methods.values()) {
            errors.addAll(md.typecheck(new Scope(scope)));
        }
        return errors;
    }
}
