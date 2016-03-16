package edu.rosehulman.minijavac;

import com.google.common.collect.ImmutableList;
import edu.rosehulman.minijavac.generated.sym;
import java_cup.runtime.Scanner;
import java_cup.runtime.Symbol;

import java.io.IOException;
import java.io.Reader;

public class Lexer implements Scanner {

    private ImmutableList<Token> tokens;
    private int index;

    public Lexer(Reader reader) throws IOException {
        this.tokens = this.lex(reader);
        this.index = 0;
    }

    private ImmutableList<Token> lex(Reader reader) throws IOException {
        edu.rosehulman.minijavac.generated.Lexer lexer =
                new edu.rosehulman.minijavac.generated.Lexer(reader);
        Symbol symbol = lexer.next_token();
        ImmutableList.Builder<Token> tokens = new ImmutableList.Builder<>();
        while (symbol.sym != sym.EOF) {
            tokens.add(new Token(symbol));
            symbol = lexer.next_token();
        }
        return tokens.build();
    }

    public ImmutableList<Token> getTokens() {
        return this.tokens;
    }

    @Override
    public Symbol next_token() throws Exception {
        return tokens.get(index).getSymbol();
    }
}
