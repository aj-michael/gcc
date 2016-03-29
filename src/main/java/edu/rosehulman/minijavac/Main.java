package edu.rosehulman.minijavac;

import edu.rosehulman.minijavac.generated.Lexer;
import edu.rosehulman.minijavac.generated.Parser;

import java.io.FileReader;

public class Main {
    public static void main(String args[]) throws Exception {
        Parser parser = new Parser(new Lexer(new FileReader(args[0])));
        parser.parse();
        System.out.println(parser.getParseLog());
    }
}
