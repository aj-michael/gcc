package edu.rosehulman.minijavac.ast;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.toMap;

public class ClassDeclaration {
    public final String name;
    public final Optional<String> parentClassName;
    public final Map<String, String> classVariableDeclarations;
    public final Map<String, MethodDeclaration> methodDeclarations;

    public ClassDeclaration(String name, Optional<String> parentClassName, Map<String, String> classVariableDeclarations,
            List<MethodDeclaration> methodDeclarations) {
        this.name = name;
        this.parentClassName = parentClassName;
        this.classVariableDeclarations = classVariableDeclarations;
        this.methodDeclarations = methodDeclarations.stream().collect(toMap(m -> m.name, m -> m));
    }
}
