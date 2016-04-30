package edu.rosehulman.minijavac.ast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;
import edu.rosehulman.minijavac.generator.ConstantPool;
import edu.rosehulman.minijavac.typechecker.Scope;
import edu.rosehulman.minijavac.typechecker.Type;

public enum LiteralKeyword implements LiteralExpression {
    THIS {
        @Override
        public List<String> typecheck(Scope scope) {
            List<String> errors = new ArrayList<>();
            return errors;
        }

        @Override
        public int maxBlockDepth() {
            return 1;
        }

        @Override
        public Type getType(Scope scope) {
            if (scope.className.equals(scope.program.mainClassDeclaration.name)) {
                // Cannot use `this` in main.
                return null;
            } else {
                return new Type(scope.className);
            }
        }

        @Override
        public void addIntegerEntries(ConstantPool cp) {
        }

        @Override
        public List<Byte> generateCode(ConstantPool cp, Map<String, Integer> variables) {
            return ImmutableList.of((byte) 42); // aload_0
        }
    }, NULL {
        @Override
        public List<String> typecheck(Scope scope) {
            List<String> errors = new ArrayList<>();
            return errors;
        }

        @Override
        public int maxBlockDepth() {
            return 1;
        }

        @Override
        public Type getType(Scope scope) {
            return Type.NULL;
        }

        @Override
        public void addIntegerEntries(ConstantPool cp) {
        }

        @Override
        public List<Byte> generateCode(ConstantPool cp, Map<String, Integer> variables) {
            return ImmutableList.of((byte) 1); // aconst_null
        }
    }
}
