package edu.rosehulman.minijavac.cli;

import edu.rosehulman.minijavac.generated.Lexer;
import io.airlift.airline.Arguments;
import io.airlift.airline.Command;
import java_cup.runtime.Symbol;

import java.io.FileNotFoundException;
import java.io.FileReader;

@Command(name = "lex", description = "Lexical analysis of Minijava program")
public class LexCommand implements Runnable {
    @Arguments(description = "File to lex")
    public String filename;

    @Override
    public void run() {
        try {
            Lexer lexer = new Lexer(new FileReader(filename));
            for (Symbol token : lexer) {
                System.out.println(token.value);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}