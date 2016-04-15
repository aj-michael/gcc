package edu.rosehulman.minijavac.ast;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ClassDeclaration {
    public final String name;
    public final Optional<String> parentClassName;
    public final Map<String, String> classVariableDeclarations;
    public final List<MethodDeclaration> methodDeclarations;

    public ClassDeclaration(String name, Optional<String> parentClassName, Map<String, String> classVariableDeclarations,
            List<MethodDeclaration> methodDeclarations) {
        this.name = name;
        this.parentClassName = parentClassName;
        this.classVariableDeclarations = classVariableDeclarations;
        for (MethodDeclaration m : methodDeclarations) {
            System.out.println(m.name);
        }
        this.methodDeclarations = methodDeclarations;
    }
}
