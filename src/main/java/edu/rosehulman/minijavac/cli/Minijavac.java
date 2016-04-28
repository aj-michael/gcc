package edu.rosehulman.minijavac.cli;

import com.google.common.io.Files;
import edu.rosehulman.minijavac.ast.Program;
import edu.rosehulman.minijavac.generated.Lexer;
import edu.rosehulman.minijavac.generated.Parser;
import edu.rosehulman.minijavac.generator.CodeGenerator;
import edu.rosehulman.minijavac.typechecker.TypeChecker;
import io.airlift.airline.Cli;
import io.airlift.airline.Help;

import java.io.File;
import java.io.FileReader;
import java.util.Map;

public class Minijavac {
    public static void main(String args[]) throws Exception {
        Cli.CliBuilder<Runnable> builder = Cli.<Runnable>builder("minijavac")
            .withDescription("A Java compiler for a worse version of Java.")
            .withDefaultCommand(Help.class)
            .withCommands(
                LexCommand.class,
                ParseCommand.class,
                TypecheckCommand.class,
                GenerateCodeCommand.class);
        builder.build().parse(args).run();
    }
}
