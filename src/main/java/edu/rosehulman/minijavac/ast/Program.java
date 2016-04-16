package edu.rosehulman.minijavac.ast;

import edu.rosehulman.minijavac.typechecker.Scope;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Program {
    public final ClassDeclaration mainClassDeclaration;
    public final List<ClassDeclaration> classDeclarations;

    public Program(ClassDeclaration mainClassDeclaration, List<ClassDeclaration> classDeclarations) {
        this.mainClassDeclaration = mainClassDeclaration;
        this.classDeclarations = classDeclarations;
    }

    public List<ClassDeclaration> getClassDeclarations() {
        ArrayList<ClassDeclaration> classDeclarations = new ArrayList<>();
        classDeclarations.add(mainClassDeclaration);
        classDeclarations.addAll(this.classDeclarations);
        return classDeclarations;
    }

    public Set<String> getClassNames() {
        HashSet<String> classNames = new HashSet<>();
        for (ClassDeclaration classDeclaration : classDeclarations) {
            classNames.add(classDeclaration.name);
        }
        classNames.add("int");
        classNames.add("boolean");
        return classNames;
    }

    public List<String> typecheck() {
        List<String> errors = new ArrayList<>();
        Scope programScope = new Scope();
        Map<String, Scope> classScopes = new HashMap<>();
        for (ClassDeclaration cd : getClassDeclarations()) {
            if (programScope.classes.containsKey(cd.name)) {
                errors.add("Class named " + cd.name + " already exists.");
            } else {
                programScope.classes.put(cd.name, cd);
                if (cd.parentClassName.isPresent() && !programScope.containsClass(cd.parentClassName.get())) {
                    errors.add("Superclass name " + cd.parentClassName.get() + " not in scope.");
                }
                Scope parentScope = cd.parentClassName.isPresent() ? classScopes.get(cd.parentClassName.get()) : programScope;
                Scope classScope = new Scope(parentScope);
                classScopes.put(cd.name, classScope);
                errors.addAll(cd.typecheck(classScope));
            }
        }
        programScope.classes.values().stream()
            .filter(cd -> cd != null)
            .forEach(cd -> errors.addAll(cd.typecheckAgain(classScopes.get(cd.name))));
        return errors;
    }
}
