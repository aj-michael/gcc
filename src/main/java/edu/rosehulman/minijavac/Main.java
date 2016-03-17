package edu.rosehulman.minijavac;

import com.google.common.collect.ImmutableList;

import java.io.FileReader;
import java.io.Reader;

public class Main {
    public static void main(String args[]) throws Exception {
        for (String arg : args) {
            Reader reader = new FileReader(arg);
            Lexer lexer = new Lexer(reader);
            ImmutableList<Token> tokens = lexer.getTokens();
            for(Token token: tokens) {
                System.out.println(token);
            }
        }
    }
}
