package edu.rosehulman.minijavac.cli;

import io.airlift.airline.Cli;
import io.airlift.airline.Help;

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
