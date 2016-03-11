package edu.rosehulman.minijavac.token;

import java.util.HashMap;
import java.util.Map;

public class OperatorToken implements Token {

    private final Operator value;

    public OperatorToken(String operator) {
        this.value = Operator.getOperator(operator);
    }

    public Operator getOperator() {
        return this.value;
    }

    public TokenSort getSort() {
        return TokenSort.OPERATOR;
    }

    @Override
    public String toString() {
        return this.value.toString();
    }

    public enum Operator {
        PLUS("+", "plus"),
        MINUS("-", "minus");

        private static final Map<String, Operator> operators;

        static {
            operators = new HashMap<String, Operator>();
            for(Operator op : Operator.values()) {
                operators.put(op.operator, op);
            }
        }

        private String operator;
        private String displayValue;

        Operator(String value, String displayValue) {
            this.operator = value;
            this.displayValue = displayValue;
        }

        @Override
        public String toString() {
            return this.displayValue;
        }

        public static Operator getOperator(String s) {
            return operators.get(s);
        }
    }
}
