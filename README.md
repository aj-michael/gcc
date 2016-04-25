# Minijavac

[![Build Status](https://img.shields.io/travis/aj-michael/minijavac.svg)](https://travis-ci.org/aj-michael/minijavac)
[![Code Coverage](https://img.shields.io/codecov/c/github/aj-michael/minijavac.svg)](https://codecov.io/github/aj-michael/minijavac)
[![MIT licensed](https://img.shields.io/badge/license-MIT-blue.svg)](https://raw.githubusercontent.com/aj-michael/minijavac/master/LICENSE)
[![GitHub release](https://img.shields.io/github/release/aj-michael/minijavac.svg)](https://github.com/aj-michael/minijavac/releases)

A Java compiler for a worse version of Java.

## Installation/Release instructions

To install, download and unzip the most recent [release](https://github.com/aj-michael/minijavac/releases). Add `export PATH="$PATH:/path/to/minijavac-x.y.z/bin"` to your bashrc or set the equivalent Windows environment variable.

## Running instructions

Minijavac has three commands: `lex`, `parse` and `typecheck`. Each takes exactly one file and writes to standard out.

    $ minijavac
    usage: minijavac <command> [<args>]

    The most commonly used minijavac commands are:
        lex         Lexical analysis of Minijava program
        parse       Parse Minijava program
        typecheck   Typecheck Minijava program

    See 'minijavac help <command>' for more information on a specific command.
