package edu.rosehulman.minijavac.ast;

import java.util.ArrayList;
import java.util.List;

import edu.rosehulman.minijavac.typechecker.Scope;

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

    public List<String> typecheck() {
        List<String> errors = new ArrayList<>();
        Scope programScope = new Scope(this);
        List<ClassDeclaration> validClasses = new ArrayList<>();
        for (ClassDeclaration cd : getClassDeclarations()) {
            if (programScope.classes.containsKey(cd.name)) {
                errors.add("Class named " + cd.name + " already exists.");
                errors.addAll(cd.typecheck(programScope.getClassScope(cd.name)));
            } else {
                Scope parentScope;
                if (!cd.parentClassName.isPresent()) {
                    parentScope = programScope;
                } else if (programScope.containsClass(cd.parentClassName.get())) {
                    parentScope = programScope.classes.get(cd.parentClassName.get());
                } else {
                    errors.add("Superclass name " + cd.parentClassName.get() + " not in scope.");
                    parentScope = programScope;
                }
                Scope classScope = new Scope(parentScope, cd);
                programScope.classes.put(cd.name, classScope);
                validClasses.add(cd);
                programScope.classes.put(cd.name, classScope);
                errors.addAll(cd.typecheck(classScope));
            }
        }
        getClassDeclarations().forEach(cd -> errors.addAll(cd.typecheckMore(programScope.classes.get(cd.name))));
        getClassDeclarations().forEach(cd -> errors.addAll(cd.typecheckAgain(programScope.classes.get(cd.name))));
        return errors;
    }
}
