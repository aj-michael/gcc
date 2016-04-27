package edu.rosehulman.minijavac.cli;

import edu.rosehulman.minijavac.ast.Program;
import edu.rosehulman.minijavac.generated.Lexer;
import edu.rosehulman.minijavac.generated.Parser;
import edu.rosehulman.minijavac.generator.CodeGenerator;
import edu.rosehulman.minijavac.typechecker.TypeChecker;
import io.airlift.airline.Arguments;
import io.airlift.airline.Command;

import java.io.FileReader;

@Command(name = "generate", description = "Bytecode generator for Minijava programs")
public class GenerateCodeCommand implements Runnable {
    @Arguments(description = "File to compile")
    public String filename;

    @Override
    public void run() {
        try {
            Lexer lexer = new Lexer(new FileReader(filename));
            Parser parser = new Parser(lexer);
            Program program = parser.parseProgram();
            program.typecheck();
            TypeChecker typechecker = new TypeChecker();
            if (!typechecker.isValid(program)) {
                typechecker.getTypeCheckerLog().forEach(System.out::println);
            } else {
                new CodeGenerator(program).generate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}