package edu.rosehulman.minijavac.cli;

import edu.rosehulman.minijavac.generated.Lexer;
import edu.rosehulman.minijavac.generated.Parser;
import io.airlift.airline.Arguments;
import io.airlift.airline.Command;

import java.io.FileReader;

@Command(name = "parse", description = "Parse Minijava program")
public class ParseCommand implements Runnable {
    @Arguments(description = "File to be parsed")
    public String filename;

    @Override
    public void run() {
        try {
            Parser parser = new Parser(new Lexer(new FileReader(filename)));
            parser.parseProgram();
            System.out.println(parser.getParseLog());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}