package edu.rosehulman.minijavac.ast;

import java.util.List;

public class MainMethodDeclaration extends MethodDeclaration {
    private static final String NAME = "main";

    public MainMethodDeclaration(List<Statement> statements) {
        super(NAME, statements);
    }
}
