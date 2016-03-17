package edu.rosehulman.minijavac;

import edu.rosehulman.minijavac.generated.Lexer;
import java_cup.runtime.Symbol;

import java.io.FileReader;
import java.io.Reader;

public class Main {
    public static void main(String args[]) throws Exception {
        for (String arg : args) {
            Reader reader = new FileReader(arg);
            Lexer lexer = new Lexer(reader);
            for (Symbol token : lexer) {
                System.out.println(token.value);
            }
        }
    }
}
