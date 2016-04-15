package edu.rosehulman.minijavac.ast;

import java.util.List;

public class Program {
    public final ClassDeclaration mainClassDeclaration;
    public final List<ClassDeclaration> classDeclarations;

    public Program(ClassDeclaration mainClassDeclaration, List<ClassDeclaration> classDeclarations) {
        this.mainClassDeclaration = mainClassDeclaration;
        this.classDeclarations = classDeclarations;
    }
}
