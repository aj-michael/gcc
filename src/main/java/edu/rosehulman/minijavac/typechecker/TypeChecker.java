package edu.rosehulman.minijavac.typechecker;

import com.google.common.base.Joiner;
import edu.rosehulman.minijavac.ast.Program;

import java.util.List;

public class TypeChecker {

    private List<String> errors;

    public boolean isValid(Program program) {
        errors = program.typecheck();
        return errors.isEmpty();
    }

    public String getTypeCheckerLog() {
        if (errors == null || errors.isEmpty()) {
            return "Success!\n";
        } else {
            return Joiner.on("\n").join(errors) + "\n";
        }
    }
}
