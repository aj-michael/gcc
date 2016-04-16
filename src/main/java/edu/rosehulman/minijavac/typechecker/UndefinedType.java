package edu.rosehulman.minijavac.typechecker;

import edu.rosehulman.minijavac.ast.AssignmentStatement;
import edu.rosehulman.minijavac.ast.ClassDeclaration;
import edu.rosehulman.minijavac.ast.VariableDeclaration;
import edu.rosehulman.minijavac.ast.MethodDeclaration;
import edu.rosehulman.minijavac.ast.Program;
import edu.rosehulman.minijavac.ast.Statement;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

public class UndefinedType implements TypeCheckerRule {
    @Override
    public List<String> apply(Program program) {
        return null;
    }
/*
    @Override
    public List<String> apply(Program program) {
        List<String> errors = new ArrayList<>();
        List<ClassDeclaration> classDeclarations = program.getClassDeclarations();
        Set<String> classNames = program.getClassNames();

        for(ClassDeclaration classDeclaration : classDeclarations) {
            for(VariableDeclaration cvd : classDeclaration.classVariableDeclarations) {
                if(!classNames.contains(cvd.type)) {
                    errors.add("Cannot find class named " + cvd.type);
                }
            }

            for(MethodDeclaration methodDeclaration : classDeclaration.methodDeclarations) {
                errors.addAll(methodDeclaration.arguments.values().stream()
                    .filter(type -> !classNames.contains(type))
                    .map(type -> "Cannot find class named " + type)
                    .collect(toList()));
                if (classDeclaration != program.mainClassDeclaration && !classNames.contains(methodDeclaration.returnType)) {
                    errors.add("Cannot find class named " + methodDeclaration.returnType);
                }
                for (Statement statement : methodDeclaration.statements) {
                    errors.addAll(apply(statement, classNames));
                }
            }
        }
        return errors;
    }

    private List<String> apply(Statement statement, Set<String> classNames) {
        List<String> errors = new ArrayList<>();
        if (statement instanceof AssignmentStatement && ((AssignmentStatement) statement).isDeclaration()) {
            AssignmentStatement assignment = (AssignmentStatement) statement;
            if (!classNames.contains(assignment.type.get())) {
                errors.add("Cannot find class named " + assignment.type.get());
            }
        }
        for (Statement substatement : statement.getSubstatements()) {
            errors.addAll(apply(substatement, classNames));
        }
        return errors;
    }
    */
}
