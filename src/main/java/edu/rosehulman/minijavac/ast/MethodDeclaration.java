package edu.rosehulman.minijavac.ast;

import java.util.LinkedHashMap;

public class MethodDeclaration {
    public final String name;
    public final String returnType;
    public final LinkedHashMap<String, String> arguments;

    public MethodDeclaration(String name, String returnType, LinkedHashMap<String, String> arguments) {
        this.name = name;
        this.returnType = returnType;
        this.arguments = arguments;
    }
}
