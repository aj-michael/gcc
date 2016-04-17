package edu.rosehulman.minijavac.typechecker;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import edu.rosehulman.minijavac.ast.ClassDeclaration;
import edu.rosehulman.minijavac.ast.Program;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

public class TypeChecker {

    //private final List<TypeCheckerRule> typeCheckerRules;
    private List<String> errors;

    /*private static final List<TypeCheckerRule> defaultRules = ImmutableList.of(
        new NonexistantSuperclass(),
        new ClassRedeclaration(),
        new FieldRedeclaration(),
        new VariableRedeclaration(),
        new UndefinedType()
    );

    public static TypeChecker createDefault() {
        return new TypeChecker(defaultRules);
    }

    public TypeChecker(List<TypeCheckerRule> rules) {
        typeCheckerRules = rules;
    }
*/
    public boolean isValid(Program program) {
        //setParentClasses(program);
        /*errors = typeCheckerRules.stream()
            .map(r -> r.apply(program))
            .flatMap(List::stream)
            .collect(toList());*/
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

    /*private static void setParentClasses(Program program) {
        Map<String, ClassDeclaration> classes = new HashMap<>();
        classes.put(program.mainClassDeclaration.name, program.mainClassDeclaration);
        for (ClassDeclaration cd : program.classDeclarations) {
            classes.put(cd.name, cd);
            if (cd.parentClassName.isPresent()) {
                cd.setParent(classes.get(cd.parentClassName.get()));
            }
        }
    }*/
}
