package edu.rosehulman.minijavac.ast;

import java.util.ArrayList;
import java.util.List;

import edu.rosehulman.minijavac.typechecker.Scope;

public enum LiteralKeyword implements LiteralExpression {
    THIS {
        @Override
        public List<String> typecheck(Scope scope) {
            List<String> errors = new ArrayList<>();
            return errors;
        }

        @Override
        public String getType(Scope scope) {
            if (scope.className.equals(scope.program.mainClassDeclaration.name)) {
                // Cannot use `this` in main.
                return null;
            } else {
                return scope.className;
            }
        }
    }, NULL {
        @Override
        public List<String> typecheck(Scope scope) {
            List<String> errors = new ArrayList<>();
            return errors;
        }

        @Override
        public String getType(Scope scope) {
            return "null";
        }
    }
}
