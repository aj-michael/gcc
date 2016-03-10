package edu.rosehulman.gcc;

import com.google.common.collect.ImmutableList;
import edu.rosehulman.gcc.token.Token;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String args[]) {
        for(String arg : args) {
            File file = new File(arg);
            try {
                Scanner scanner = new Scanner(file);
                String text = "";
                while (scanner.hasNextLine()){
                    text += scanner.nextLine();
                }
                Lexer lexer = new Lexer(text);
                ImmutableList<Token> tokens = lexer.getTokens();
                for(Token token : tokens) {
                    System.out.println(token);
                }
            } catch (FileNotFoundException e) {
                System.out.println("File " + arg + " was not found.");
            }
        }
    }
}
