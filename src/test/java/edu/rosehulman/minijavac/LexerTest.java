package edu.rosehulman.minijavac;

import com.google.common.collect.ImmutableList;
import edu.rosehulman.minijavac.token.IntegerToken;
import edu.rosehulman.minijavac.token.OperatorToken;
import edu.rosehulman.minijavac.token.Token;
import edu.rosehulman.minijavac.token.TokenSort;
import org.junit.Assert;
import org.junit.Test;

public class LexerTest {

    @Test
    public void integerTest() {
        Lexer lexer = new Lexer("12345");
        ImmutableList<Token> tokens = lexer.getTokens();
        Assert.assertEquals(1, tokens.size());
        testInteger(tokens.get(0), 12345);
    }

    @Test
    public void plusTest() {
        Lexer lexer = new Lexer("+");
        ImmutableList<Token> tokens = lexer.getTokens();
        Assert.assertEquals(1, tokens.size());
        testOperator(tokens.get(0), OperatorToken.Operator.PLUS, "plus");
    }

    @Test
    public void minusTest() {
        Lexer lexer = new Lexer("-");
        ImmutableList<Token> tokens = lexer.getTokens();
        Assert.assertEquals(1, tokens.size());
        testOperator(tokens.get(0), OperatorToken.Operator.MINUS, "minus");
    }

    private void testInteger(Token token, int i) {
        Assert.assertEquals(TokenSort.INTEGER, token.getSort());
        Assert.assertEquals(i, ((IntegerToken) token).getValue());
        Assert.assertEquals(Integer.toString(i), token.toString());
    }

    private void testOperator(Token token, OperatorToken.Operator operator, String displayValue) {
        Assert.assertEquals(TokenSort.OPERATOR, token.getSort());
        Assert.assertEquals(operator, ((OperatorToken) token).getOperator());
        Assert.assertEquals(displayValue, token.toString());
    }

    @Test
    public void multipleTokensTest() {
        Lexer lexer = new Lexer("123+321-+12");
        ImmutableList<Token> tokens = lexer.getTokens();
        Assert.assertEquals(6, tokens.size());
        testInteger(tokens.get(0), 123);
        testOperator(tokens.get(1), OperatorToken.Operator.PLUS, "plus");
        testInteger(tokens.get(2), 321);
        testOperator(tokens.get(3), OperatorToken.Operator.MINUS, "minus");
        testOperator(tokens.get(4), OperatorToken.Operator.PLUS, "plus");
        testInteger(tokens.get(5), 12);
    }
}
