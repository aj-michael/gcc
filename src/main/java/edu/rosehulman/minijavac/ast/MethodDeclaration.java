package edu.rosehulman.minijavac.ast;

import java.util.LinkedHashMap;
import java.util.List;

public class MethodDeclaration {
    public final String name;
    public final String returnType;
    public final LinkedHashMap<String, String> arguments;
    public final List<Statement> statements;
    public final Expression returnExpression;

    public MethodDeclaration(String name, List<Statement> statements) {
        this(name, new LinkedHashMap<>(), statements, null, null);
    }

    public MethodDeclaration(String name, LinkedHashMap<String, String> arguments, List<Statement> statements, String returnType, Expression returnExpression) {
        this.name = name;
        this.returnType = returnType;
        this.arguments = arguments;
        this.statements = statements;
        this.returnExpression = returnExpression;
    }
}
