package edu.rosehulman.minijavac;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableList;
import com.google.common.io.Files;

import edu.rosehulman.minijavac.token.Token;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String args[]) throws IOException {
        for(String arg : args) {
            File file = new File(arg);
            String text = Files.toString(file, Charsets.UTF_8).trim();
            Lexer lexer = new Lexer(text);
            ImmutableList<Token> tokens = lexer.getTokens();
            for(Token token : tokens) {
                System.out.println(token);
            }
        }
    }
}
