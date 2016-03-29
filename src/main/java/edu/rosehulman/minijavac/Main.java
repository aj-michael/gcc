package edu.rosehulman.minijavac;

import edu.rosehulman.minijavac.generated.Lexer;
import edu.rosehulman.minijavac.generated.Parser;
import edu.rosehulman.minijavac.generated.Symbols;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.Symbol;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedList;

public class Main {
    public static void main(String args[]) throws Exception {
        ComplexSymbolFactory csf = new ComplexSymbolFactory();
        Lexer lexer = new Lexer(new FileReader("src/test/resources/parser/java/testcase04_04.java"));
        for (Symbol symbol : lexer) {
            System.out.println(Symbols.terminalNames[symbol.sym]);
        }
    }
}
