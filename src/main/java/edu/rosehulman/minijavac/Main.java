package edu.rosehulman.minijavac;

import com.google.common.collect.ImmutableList;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class Main {
    public static void main(String args[]) throws IOException {
        for (String arg : args) {
            Reader reader = new FileReader(arg);
            ImmutableList<Token> tokens = new Lexer(reader).getTokens();
            for(Token token: tokens) {
                System.out.println(token);
            }
        }
    }
}
