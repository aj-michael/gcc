package edu.rosehulman.minijavac;

import com.google.common.collect.ImmutableList;
import edu.rosehulman.minijavac.generated.Symbols;
import java_cup.runtime.Symbol;

import java.io.IOException;
import java.io.Reader;

public class Lexer {

    private ImmutableList<Token> tokens;

    public Lexer(Reader reader) throws IOException {
         this.tokens = this.lex(reader);
    }

    private ImmutableList<Token> lex(Reader reader) throws IOException {
        edu.rosehulman.minijavac.generated.Lexer lexer =
                new edu.rosehulman.minijavac.generated.Lexer(reader);
        Symbol symbol = lexer.next_token();
        ImmutableList.Builder<Token> tokens = new ImmutableList.Builder<>();
        while (symbol.sym != Symbols.EOF) {
            tokens.add(new Token(symbol));
            symbol = lexer.next_token();
        }
        return tokens.build();
    }

    public ImmutableList<Token> getTokens() {
        return this.tokens;
    }
}
