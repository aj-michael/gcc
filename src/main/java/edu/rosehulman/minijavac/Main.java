package edu.rosehulman.minijavac;

import edu.rosehulman.minijavac.ast.Program;
import edu.rosehulman.minijavac.generated.Lexer;
import edu.rosehulman.minijavac.generated.Parser;
import edu.rosehulman.minijavac.typechecker.TypeChecker;
import io.airlift.airline.*;
import java_cup.runtime.Symbol;

import javax.inject.Inject;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Main {

    @Inject
    HelpOption helpOption;

    public static void main(String args[]) throws Exception {
        Cli.CliBuilder<Runnable> builder = Cli.<Runnable>builder("minijavac")
            .withDescription("A Java compiler for a worse version of Java.")
            .withDefaultCommand(Help.class)
            .withCommands(Lex.class, Parse.class, Typecheck.class);
        Cli<Runnable> minijavacParser = builder.build();
        minijavacParser.parse(args).run();
    }

    @Command(name = "lex", description = "Lexical analysis of Minijava program")
    public static class Lex implements Runnable {
        @Arguments(description = "File to be lexed")
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

    @Command(name = "parse", description = "Parse Minijava program")
    public static class Parse implements Runnable {
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

    @Command(name = "typecheck", description = "Typecheck Minijava program")
    public static class Typecheck implements Runnable {
        @Arguments(description = "File to be typechecked")
        public String filename;

        @Override
        public void run() {
            try {
                Parser parser = new Parser(new Lexer(new FileReader(filename)));
                Program program = parser.parseProgram();
                TypeChecker typechecker = new TypeChecker();
                typechecker.isValid(program);
                typechecker.getTypeCheckerLog().forEach(System.out::println);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
