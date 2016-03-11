package edu.rosehulman.minijavac;

import com.google.common.collect.ImmutableList;
import edu.rosehulman.minijavac.token.IntegerToken;
import edu.rosehulman.minijavac.token.OperatorToken;
import edu.rosehulman.minijavac.token.Token;

public class Lexer {

    private ImmutableList<Token> tokens;

    public Lexer(String input) {
         this.tokens = this.parse(input);
    }

    private ImmutableList<Token> parse(String input) {
        ImmutableList.Builder<Token> tokens = new ImmutableList.Builder<Token>();
        String currentToken = "";
        for(char c: input.toCharArray()) {
            if(c == '+' || c == '-') {
                if(!currentToken.isEmpty()) {
                    Token token = new IntegerToken(currentToken);
                    tokens.add(token);
                    currentToken = "";
                }
                Token token = new OperatorToken(Character.toString(c));
                tokens.add(token);
            } else {
                currentToken += c;
            }
        }

        if(!currentToken.isEmpty()) {
            Token token = new IntegerToken(currentToken);
            tokens.add(token);
        }

        return tokens.build();
    }

    public ImmutableList<Token> getTokens() {
        return this.tokens;
    }
}
