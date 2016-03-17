package edu.rosehulman.minijavac;

import edu.rosehulman.minijavac.generated.Lexer;

import java_cup.runtime.Symbol;

class Token {

    private final Symbol symbol;
    private final Lexer.NameAndValue nv;

    Token(Symbol symbol) {
        this.symbol = symbol;
        this.nv = (Lexer.NameAndValue) symbol.value;
    }

    Symbol getSymbol() {
        return this.symbol;
    }

    @Override public String toString() {
        return nv.name + ", " + nv.value;
    }
}
