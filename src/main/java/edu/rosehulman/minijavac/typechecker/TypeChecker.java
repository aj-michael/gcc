package edu.rosehulman.minijavac.typechecker;

import edu.rosehulman.minijavac.ast.Program;

import java.util.Arrays;
import java.util.List;

public class TypeChecker {

    private List<String> errors;

    public boolean isValid(Program program) {
        errors = program.typecheck();
        return errors.isEmpty();
    }

    public List<String> getTypeCheckerLog() {
        if (errors == null || errors.isEmpty()) {
            return Arrays.asList("Success!");
        } else {
            return errors;
        }
    }
}
