package edu.rosehulman.minijavac.typechecker;

import edu.rosehulman.minijavac.ast.ClassDeclaration;
import edu.rosehulman.minijavac.ast.Program;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ClassRedeclaration implements TypeCheckerRule {

    @Override
    public List<String> apply(Program program) {
        HashSet<String> classNames = new HashSet<>();
        classNames.add(program.mainClassDeclaration.name);
        List<String> errors = new ArrayList<>();
        for (ClassDeclaration classDeclaration : program.classDeclarations) {
            if (!classNames.add(classDeclaration.name)) {
                errors.add("Class named " + classDeclaration.name + " already exists.");
            }
        }
        return errors;
    }
}
