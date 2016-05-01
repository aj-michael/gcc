package edu.rosehulman.minijavac.generator;

import edu.rosehulman.minijavac.typechecker.Type;

/**
 * A class representing java bytecode variables. Mainly used to determine if integer of reference
 * instructions should be used.
 */
public class Variable {
    final String name;
    final Integer position;
    final Type type;

    public Variable(String name, Type type, int position) {
        this.name = name;
        this.position = position;
        this.type = type;
    }

    public String getName() {
        return this.name;
    }

    public Type getType() {
        return type;
    }

    public Integer getPosition() {
        return position;
    }

    @Override
    public boolean equals(Object other) {
        return (other instanceof Variable) && ((Variable) other).getName().equals(getName());
    }

    @Override
    public int hashCode() {
        return getName().hashCode();
    }
}
