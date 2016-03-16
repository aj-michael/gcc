package edu.rosehulman.minijavac;

import com.google.common.collect.ImmutableList;

import edu.rosehulman.minijavac.generated.parser;
import java_cup.runtime.ComplexSymbolFactory;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class Main {
    public static void main(String args[]) throws Exception {
        for (String arg : args) {
            Reader reader = new FileReader(arg);
            //edu.rosehulman.minijavac.Lexer lexer = new edu.rosehulman.minijavac.Lexer(reader);
            Lexer lexer = new Lexer(reader);
            //parser parser = new parser(new edu.rosehulman.minijavac.generated.Lexer(reader));
            //parser.parse();
            ImmutableList<Token> tokens = lexer.getTokens();
            for(Token token: tokens) {
                System.out.println(token);
            }
        }
    }
}
