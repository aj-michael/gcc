# Minijavac

[![Build Status](https://img.shields.io/travis/aj-michael/minijavac.svg)](https://travis-ci.org/aj-michael/minijavac)
[![Code Coverage](https://img.shields.io/codecov/c/github/aj-michael/minijavac.svg)](https://codecov.io/github/aj-michael/minijavac)
[![MIT licensed](https://img.shields.io/badge/license-MIT-blue.svg)](https://raw.githubusercontent.com/aj-michael/minijavac/master/LICENSE)
[![GitHub release](https://img.shields.io/github/release/aj-michael/minijavac.svg)](https://github.com/aj-michael/minijavac/releases)

A Java compiler for a worse version of Java.

## Installation/Release instructions

To install, download and unzip the most recent [release](https://github.com/aj-michael/minijavac/releases). Add `export PATH="$PATH:/path/to/minijavac-x.y.z/bin"` to your bashrc or set the equivalent Windows environment variable.

To cut a new release, use `gradle distZip` or `gradle distTar`.

## Development build instructions

Build with `gradle build`. Generate the lexer and parser with `gradle generate`. Run tests with `gradle test`. Build an executable jar with `gradle build`. Run the jar with `java -jar build/libs/minijavac-x.y.z.jar sample_inputs/testfile1.java`.
