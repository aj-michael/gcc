package edu.rosehulman.minijavac;

import edu.rosehulman.minijavac.ast.Program;
import edu.rosehulman.minijavac.generated.Lexer;
import edu.rosehulman.minijavac.generated.Parser;

import java.io.FileReader;

public class Main {
    public static void main(String args[]) throws Exception {
        Parser parser = new Parser(new Lexer(new FileReader("src/test/resources/parser/java/testcase00_09.java")));
        Program program = parser.parseProgram();
        System.out.println(parser.getParseLog());
        System.out.println(program);
    }
}
