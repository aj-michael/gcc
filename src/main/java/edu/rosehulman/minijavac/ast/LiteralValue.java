package edu.rosehulman.minijavac.ast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;
import edu.rosehulman.minijavac.generator.ConstantPool;
import edu.rosehulman.minijavac.generator.Variable;
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
    public void addConstantPoolEntries(ConstantPool cp) {
        if (type.equals(Type.INT)) {
            cp.integerEntry((Integer) value);
        }
    }

    @Override
    public int maxBlockDepth() {
        return 1;
    }

    @Override
    public List<Byte> generateCode(ConstantPool cp, Map<String, Variable> variables) {
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
                return ImmutableList.of((byte) 18, (byte) cp.integerEntry(integer).index);
            }
        } else if(type.equals(Type.DOUBLE)) {
            Double d = (Double) value;
            if(d == 0.0) {
                return ImmutableList.of((byte) 14); // dconst_0
            } else if(d == 1.0) {
                return ImmutableList.of((byte) 15); // dconst_1
            } else {
                return ImmutableList.of((byte) 20, (byte) 0, (byte) cp.doubleEntry(d).index);
            }
        } else if(type.equals(Type.FLOAT)) {
            Float f = (Float) value;
            if(f == 0.0) {
                return ImmutableList.of((byte) 11); // fconst_0
            } else if(f == 1.0) {
                return ImmutableList.of((byte) 12); // fconst_1
            } else if(f == 2.0) {
                return ImmutableList.of((byte) 13); // fconst_2
            } else {
                return ImmutableList.of((byte) 18, (byte) cp.floatEntry(f).index);
            }
        } else if(type.equals(Type.LONG)) {
            Long l = (Long) value;
            if(l == 0 || l == 1) {
                return ImmutableList.of((byte) (9 + l)); // lconst_0 and lconst_1
            } else {
                return ImmutableList.of((byte) 20, (byte) 0, (byte) cp.longEntry(l).index);
            }
        } else {
            throw new RuntimeException("Illegal literal value of type " + type.toString());
        }
    }
}
