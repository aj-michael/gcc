package edu.rosehulman.minijavac;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class Main {
    public static void main(String args[]) throws IOException {
        for (String arg : args) {
            Reader reader = new FileReader(arg);
            new Lexer(reader).getTokens().forEach(System.out::println);
        }
    }
}
