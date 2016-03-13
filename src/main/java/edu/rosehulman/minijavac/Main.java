package edu.rosehulman.minijavac;

import java_cup.runtime.Symbol;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import edu.rosehulman.minijavac.generated.Lexer;
import edu.rosehulman.minijavac.generated.sym;

public class Main {
    public static void main(String args[]) throws IOException {
        for (String arg : args) {
            Reader reader = new FileReader(arg);
            Lexer lexer = new Lexer(reader);
            Symbol token = lexer.next_token();
            while (token.sym != sym.EOF) {
                System.out.println(token.toString());
                token = lexer.next_token();
            }
        }
    }
}
