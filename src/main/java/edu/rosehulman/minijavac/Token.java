package edu.rosehulman.minijavac;

import edu.rosehulman.minijavac.generated.Symbols;
import java_cup.runtime.Symbol;

class Token {

    private final Symbol symbol;

    Token(Symbol symbol) {
        this.symbol = symbol;
    }

    @Override public String toString() {
        return Symbols.terminalNames[symbol.sym] + ", " + symbol.value;
    }
}
