package edu.rosehulman.minijavac;

import edu.rosehulman.minijavac.ast.Program;
import edu.rosehulman.minijavac.generated.Lexer;
import edu.rosehulman.minijavac.generated.Parser;
import edu.rosehulman.minijavac.typechecker.TypeChecker;

import java.io.FileReader;

public class Main {
    public static void main(String args[]) throws Exception {
        Parser parser = new Parser(new Lexer(new FileReader(args[0])));
        Program program = parser.parseProgram();
        TypeChecker typechecker = new TypeChecker();
        typechecker.isValid(program);
        System.out.println(typechecker.getTypeCheckerLog());
    }
}
