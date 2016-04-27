package edu.rosehulman.minijavac.cli;

import edu.rosehulman.minijavac.ast.Program;
import edu.rosehulman.minijavac.generated.Lexer;
import edu.rosehulman.minijavac.generated.Parser;
import edu.rosehulman.minijavac.typechecker.TypeChecker;
import io.airlift.airline.Arguments;
import io.airlift.airline.Command;

import java.io.FileReader;

@Command(name = "typecheck", description = "Typecheck Minijava program")
public class TypecheckCommand implements Runnable {
    @Arguments(description = "File to be typechecked")
    public String filename;

    @Override
    public void run() {
        try {
            Parser parser = new Parser(new Lexer(new FileReader(filename)));
            Program program = parser.parseProgram();
            TypeChecker typechecker = new TypeChecker();
            typechecker.isValid(program);
            typechecker.getTypeCheckerLog().forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}