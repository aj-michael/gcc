package edu.rosehulman.minijavac.ast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;
import edu.rosehulman.minijavac.generator.ConstantPool;
import edu.rosehulman.minijavac.typechecker.Scope;
import edu.rosehulman.minijavac.typechecker.Type;

public class LiteralValue implements LiteralExpression {
    public final Type type;
    public final Object value;

    public LiteralValue(String type, Object value) {
        if (type.equals(Type.INT.toString())) {
            this.type = Type.INT;
        } else if (type.equals(Type.BOOLEAN.toString())) {
            this.type = Type.BOOLEAN;
        } else {
            this.type = new Type(type);
        }
        this.value = value;
    }

    @Override
    public List<String> typecheck(Scope scope) {
        List<String> errors = new ArrayList<>();
        return errors;
    }

    @Override
    public Type getType(Scope scope) {
        return type;
    }

    @Override
    public void addIntegerEntries(ConstantPool cp) {
        if (type.equals(Type.INT)) {
            cp.integerEntry((Integer) value);
        }
    }

    @Override
    public List<Byte> generateCode(ConstantPool cp, Map<String, Integer> variables) {
        if(type.equals(Type.BOOLEAN)) {
            if(value.equals(Boolean.FALSE)) {
                return ImmutableList.of((byte) 3);
            } else {
                return ImmutableList.of((byte) 4);
            }
        } else if(type.equals(Type.INT)) {
            Integer integer = (Integer) value;
            if(integer >= -1 && integer <= 5) {
                return ImmutableList.of((byte) (3 + integer));
            } else if(integer >= Byte.MIN_VALUE && integer <= Byte.MAX_VALUE) {
                return ImmutableList.of((byte) 16, integer.byteValue());
            } else {
                return ImmutableList.of((byte) 18, (byte) cp.integerEntry((Integer) value).index);
            }
        } else {
            throw new RuntimeException("Illegal literal value of type " + type.toString());
        }
    }
}
