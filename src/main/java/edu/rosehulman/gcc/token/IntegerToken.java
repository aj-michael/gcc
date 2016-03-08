package edu.rosehulman.gcc.token;

public class IntegerToken implements Token {

    private final int value;

    public IntegerToken(String integer) {
        this.value = Integer.parseInt(integer);
    }

    public int getValue() {
        return this.value;
    }

    public TokenSort getSort() {
        return TokenSort.INTEGER;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }
}
