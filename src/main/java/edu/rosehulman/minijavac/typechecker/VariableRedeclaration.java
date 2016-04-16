package edu.rosehulman.minijavac.typechecker;

import edu.rosehulman.minijavac.ast.Program;
import edu.rosehulman.minijavac.ast.Statement;

import java.util.ArrayList;
import java.util.List;

public class VariableRedeclaration implements TypeCheckerRule {
    @Override
    public List<String> apply(Program program) {
        List<String> errors = new ArrayList<>();
                /*
        Scope mainScope = new Scope();
        for (Statement statement : program.mainClassDeclaration.methodDeclarations.get(0).statements) {
            errors.addAll(applyStatement(mainScope, statement));
        }
        for (ClassDeclaration cd : program.classDeclarations) {
            Map<String, String> classVariables = new HashMap<>();
            for (VariableDeclaration cvd : cd.getClassVariables()) {
                classVariables.put(cvd.name, cvd.type);
            }
            Scope classScope = new Scope(classVariables);
            for (MethodDeclaration md : cd.methodDeclarations) {
                Scope methodScope = new Scope(classScope, md.arguments);
                for (Statement statement : md.statements) {
                    errors.addAll(applyStatement(methodScope, statement));
                }
            }
        }
        */
        return errors;
    }

    private static List<String> applyStatement(Scope scope, Statement statement) {
        List<String> errors = new ArrayList<>();
        /*
        Scope evaluationScope = scope;
        if (statement instanceof AssignmentStatement && ((AssignmentStatement) statement).isDeclaration()) {
            AssignmentStatement assignment = (AssignmentStatement) statement;
            if (!scope.add(assignment.id, assignment.type.get())) {
                errors.add("The variable " + assignment.id + " is already declared in the current scope.");
            }
        } else if(statement instanceof BlockStatement) {
            evaluationScope = new Scope(scope, new HashMap<>());
        }

        for(Statement substatement : statement.getSubstatements()) {
            errors.addAll(applyStatement(evaluationScope, substatement));
        }
        */

        return errors;
    }
}
