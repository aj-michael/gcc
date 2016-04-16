package edu.rosehulman.minijavac.typechecker;

import edu.rosehulman.minijavac.ast.Program;

import java.util.List;
import java.util.function.Function;

public interface TypeCheckerRule extends Function<Program, List<String>> {
    List<String> apply(Program program);
}
