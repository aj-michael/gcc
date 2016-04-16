package edu.rosehulman.minijavac.typechecker;

import edu.rosehulman.minijavac.ast.ClassDeclaration;
import edu.rosehulman.minijavac.ast.MethodDeclaration;
import edu.rosehulman.minijavac.ast.Program;

import java.util.ArrayList;
import java.util.List;

public class MethodRedeclaration implements TypeCheckerRule {

    @Override
    public List<String> apply(Program program) {
        List<String> errors = new ArrayList<>();

        List<ClassDeclaration> classDeclarations = program.getClassDeclarations();

        for(ClassDeclaration classDeclaration : classDeclarations) {
            for(MethodDeclaration methodDeclaration : classDeclaration.methodDeclarations) {

            }
        }

        return errors;
    }
}
