package edu.rosehulman.minijavac.typechecker;

import edu.rosehulman.minijavac.ast.ClassDeclaration;
import edu.rosehulman.minijavac.ast.Program;

import java.util.ArrayList;
import java.util.List;

public class NonexistantSuperclass implements TypeCheckerRule {
    @Override
    public List<String> apply(Program program) {
        List<String> errors = new ArrayList<>();
        for (ClassDeclaration cd : program.classDeclarations) {
            if (cd.parentClassName.isPresent() && cd.parent == null) {
                errors.add("Superclass name " + cd.parentClassName.get() + " not in scope.");
            }
        }
        return errors;
    }
}
