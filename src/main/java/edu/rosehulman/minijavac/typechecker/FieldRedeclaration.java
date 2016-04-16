package edu.rosehulman.minijavac.typechecker;

import edu.rosehulman.minijavac.ast.ClassDeclaration;
import edu.rosehulman.minijavac.ast.VariableDeclaration;
import edu.rosehulman.minijavac.ast.Program;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class FieldRedeclaration implements TypeCheckerRule {

    @Override
    public List<String> apply(Program program) {
        List<String> errors = new ArrayList<>();
        for (ClassDeclaration classDeclaration : program.classDeclarations) {
            HashSet<String> classVariables = new HashSet<>();
            for (VariableDeclaration cvd : classDeclaration.getClassVariables()) {
                if (!classVariables.add(cvd.name)) {
                    errors.add("The class variable " + cvd.name + " is already declared.  Redeclaration and shadowing are not allowed.");
                }
            }
        }
        return errors;
    }
}
