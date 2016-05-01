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
        this.type = Type.of(type);
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
    public int maxBlockDepth() {
        return 1;
    }

    @Override
    public List<Byte> generateCode(ConstantPool cp, Map<String, Integer> variables) {
        if(type.equals(Type.BOOLEAN)) {
            if(value.equals(Boolean.FALSE)) {
                return ImmutableList.of((byte) 3); // iconst_0
            } else {
                return ImmutableList.of((byte) 4); // iconst_1
            }
        } else if(type.equals(Type.INT)) {
            Integer integer = (Integer) value;
            if(integer >= -1 && integer <= 5) {
                return ImmutableList.of((byte) (3 + integer)); // iconst_m1 through iconst_5
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
