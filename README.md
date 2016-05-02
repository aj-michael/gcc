# Minijavac

[![Build Status](https://img.shields.io/travis/aj-michael/minijavac.svg)](https://travis-ci.org/aj-michael/minijavac)
[![codecov](https://codecov.io/gh/aj-michael/minijavac/branch/master/graph/badge.svg)](https://codecov.io/gh/aj-michael/minijavac)
[![MIT licensed](https://img.shields.io/badge/license-MIT-blue.svg)](https://raw.githubusercontent.com/aj-michael/minijavac/master/LICENSE)
[![GitHub release](https://img.shields.io/github/release/aj-michael/minijavac.svg)](https://github.com/aj-michael/minijavac/releases)

A Java compiler for a worse version of Java.

## Installation/Release instructions

To install, download and unzip the most recent [release](https://github.com/aj-michael/minijavac/releases). Add `export PATH="$PATH:/path/to/minijavac-x.y.z/bin"` to your bashrc or set the equivalent Windows environment variable.

## Running instructions

Minijavac has four commands: `generate`, `lex`, `parse` and `typecheck`. Each takes exactly one file as a command line argument. `generate` will create one `.class` file for each Java class in the source file. The other commands write to standard out.

    $ minijavac
    usage: minijavac <command> [<args>]

    The most commonly used minijavac commands are:
        generate    Generate bytecode for Minijava programs
        lex         Lexical analysis of Minijava program
        parse       Parse Minijava program
        typecheck   Typecheck Minijava program

    See 'minijavac help <command>' for more information on a specific command.

Minijavac requires Java 1.5 or execute the bytecode from the `generate` command.
