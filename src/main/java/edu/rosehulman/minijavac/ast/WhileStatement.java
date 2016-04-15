package edu.rosehulman.minijavac.ast;

public class WhileStatement implements Statement {
    public final Expression condition;
    public final Statement statement;

    public WhileStatement(Expression condition, Statement statement) {
        this.condition = condition;
        this.statement = statement;
    }
}
