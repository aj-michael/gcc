package edu.rosehulman.minijavac;

import edu.rosehulman.minijavac.generated.Lexer;
import edu.rosehulman.minijavac.generated.Parser;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.Symbol;

import java.io.FileReader;

public class Main {
    public static void main(String args[]) throws Exception {
        ComplexSymbolFactory csf = new ComplexSymbolFactory();
        Parser parser = new Parser(new Lexer(new FileReader("src/test/resources/parser/java/testcase00_03.java")));
        Symbol symbol = parser.parse();
        System.out.println(parser.getParseLog());
    }
}
