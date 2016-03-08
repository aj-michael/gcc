package edu.rosehulman.gcc;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableList;
import com.google.common.io.Resources;
import edu.rosehulman.gcc.token.Token;

import java.io.IOException;
import java.net.URL;

public class Main {
    public static void main(String args[]) {
        for(String arg : args) {
            URL url = Resources.getResource(arg);
            try {
                String text = Resources.toString(url, Charsets.UTF_8);
                Lexer lexer = new Lexer(text);
                ImmutableList<Token> tokens = lexer.getTokens();
                for(Token token : tokens) {
                    System.out.println(token);
                }
            } catch (IOException e) {
                System.out.println("File " + url + " was not found.");
            }
        }
    }
}
