package edu.rosehulman.minijavac.ast;

import edu.rosehulman.minijavac.generator.ClassEntry;
import edu.rosehulman.minijavac.generator.ConstantPool;
import edu.rosehulman.minijavac.typechecker.Scope;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

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
            }
            if (scope.containsMethod(md.name) && !md.canOverride(scope.getMethod(md.name))) {
                errors.add("Cannot overload methods.  Method " + md.name + " has different type signature than inherited method of the same name.");
            }
            scope.methods.put(md.name, md);
        }

        return errors;
    }

    public List<String> typecheckMore(Scope scope) {
        List<String> errors = methodDeclarations.stream()
                .filter(md -> !(md instanceof MainMethodDeclaration) && !scope.containsClass(md.returnType.getDescriptor()))
                .map(md -> "Cannot find class named " + md.returnType)
                .collect(toList());
        return errors;
    }

    public List<String> typecheckAgain(Scope scope) {
        List<String> errors = new ArrayList<>();
        for (VariableDeclaration cvd : classVariableDeclarations) {
            if (scope.containsVariable(cvd.name)) {
                errors.add("The class variable " + cvd.name + " is already declared.  Redeclaration and shadowing are not allowed.");
            } else if (!scope.containsClass(cvd.type.getDescriptor())) {
                errors.add("Cannot find class named " + cvd.type);
            } else {
                scope.declaredVariables.put(cvd.name, cvd.type);
            }
        }
        for (MethodDeclaration md : methodDeclarations) {
            errors.addAll(md.typecheck(new Scope(scope)));
        }
        return errors;
    }

    public String getParentClass() {
        if (parentClassName.isPresent()) {
            return parentClassName.get();
        } else {
            return "java/lang/Object";
        }
    }

    public ConstantPool getConstantPool() {
        ConstantPool cp = new ConstantPool();
        ClassEntry classEntry = cp.classEntry(this);
        for (MethodDeclaration md : methodDeclarations) {
            cp.methodRefEntry(name, md.name, md.getDescriptor());
            md.addConstantPoolEntries(cp);
        }

        for (VariableDeclaration vd : classVariableDeclarations) {
            cp.thisFieldRefEntry(name, vd.name, vd.getDescriptor());
        }
        return cp;
    }
}
