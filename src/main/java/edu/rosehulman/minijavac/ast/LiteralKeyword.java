package edu.rosehulman.minijavac.ast;

import edu.rosehulman.minijavac.typechecker.Scope;

import java.util.ArrayList;
import java.util.List;

public enum LiteralKeyword implements LiteralExpression {
    THIS {
        @Override
        public List<String> typecheck(Scope scope) {
            List<String> errors = new ArrayList<>();
            return errors;
        }

        @Override
        public String getType(Scope scope) {
            return "this";
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
