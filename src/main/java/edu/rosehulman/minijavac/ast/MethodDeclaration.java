package edu.rosehulman.minijavac.ast;

import com.google.common.primitives.Bytes;
import edu.rosehulman.minijavac.generator.ConstantPool;
import edu.rosehulman.minijavac.generator.Variable;
import edu.rosehulman.minijavac.typechecker.DoubleType;
import edu.rosehulman.minijavac.typechecker.LongType;
import edu.rosehulman.minijavac.typechecker.Scope;
import edu.rosehulman.minijavac.typechecker.Type;

import java.nio.ByteBuffer;
import java.util.*;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

public class MethodDeclaration {
    public final String name;
    public final Type returnType;
    public final List<VariableDeclaration> arguments;
    public final List<Statement> statements;
    public final Expression returnExpression;
    public final boolean isSynchronized;

    public MethodDeclaration(String name, List<Statement> statements) {
        this(name, new LinkedList<>(), statements, null, null, false);
    }

    public MethodDeclaration(String name, List<VariableDeclaration> arguments, List<Statement> statements, String returnType, Expression returnExpression, boolean isSynchronized) {
        this.name = name;
        this.returnType = Type.of(returnType);
        this.arguments = arguments;
        this.statements = statements;
        this.returnExpression = returnExpression;
        this.isSynchronized = isSynchronized;
    }

    public boolean canOverride(MethodDeclaration other) {
        Iterator<VariableDeclaration> arguments = this.arguments.iterator();
        Iterator<VariableDeclaration> otherArguments = other.arguments.iterator();
        while (arguments.hasNext() && otherArguments.hasNext()) {
            if (!arguments.next().type.equals(otherArguments.next().type)) {
                return false;
            }
        }
        if (arguments.hasNext() || otherArguments.hasNext()) {
            return false;
        }
        return returnType.equals(other.returnType);
    }

    public List<String> typecheck(Scope scope) {
        List<String> errors = new ArrayList<>();

        Set<String> argumentNames = new HashSet<String>();
        for (VariableDeclaration argument : arguments) {
            if (argumentNames.contains(argument.name)) {
                errors.add("Formal parameter named " + argument.name + " duplicates the name of another formal parameter.");
            } else if (!scope.containsClass(argument.type.type)) {
                errors.add("Cannot find class named " + argument.type);
            } else {
                scope.declaredVariables.put(argument.name, argument.type);
            }
            argumentNames.add(argument.name);
        }
        for (Statement statement : statements) {
            errors.addAll(statement.typecheck(scope));
        }
        if (returnExpression != null) {
            errors.addAll(returnExpression.typecheck(scope));
        }
        if ((returnExpression != null) && !returnExpression.getType(scope).isA(returnType, scope)) {
            errors.add(
                "Actual return type " + returnExpression.getType(scope) + " of method " + name +
                    " does not match declared type " + returnType);
        }
        return errors;
    }

    private static String formatType(Type type) {
        if (type == Type.NULL || type.isPrimitiveType()) {
            return type.getDescriptor();
        } else {
            return "L" + type.getDescriptor() + ";";
        }
    }

    public String getDescriptor() {
        StringBuilder builder = new StringBuilder()
            .append("(");
        for (VariableDeclaration argument : arguments) {
            builder.append(formatType(argument.type));
        }
        if (this instanceof MainMethodDeclaration) {
            builder.append("[Ljava/lang/String;");
        }
        builder.append(")")
            .append(formatType(returnType));
        return builder.toString();
    }

    public int numArguments() {
        return (this instanceof MainMethodDeclaration) ? 1 + arguments.size() : arguments.size();
    }

    public int numLocalVariables(List<Variable> variableDeclarations) {
        int num =  numArguments() + 1;
        for (VariableDeclaration vd : arguments) {
            variableDeclarations.add(new Variable(vd.name, vd.type, variableDeclarations.size()+1));
            if(vd.type instanceof DoubleType || vd.type instanceof LongType) {
                variableDeclarations.add(null);
                num++;
            }
        }

        for (Statement statement : statements) {
            num += statement.numLocalVariables(variableDeclarations);
        }
        return num;
    }

    public int maxBlockDepth() {
        int depth = 0;
        for(Statement statement : statements) {
            depth += statement.maxBlockDepth();
        }
        return depth;
    }

    public byte[] getBytes(ConstantPool cp) {
        short maxDepth = (short) maxBlockDepth();
        List<Variable> vds = new ArrayList<>();
        short numLocalVariables = (short) numLocalVariables(vds);
        Map<String, Variable> variableNameToIndex = vds.stream()
                .filter(Objects::nonNull)
                .distinct()
                .collect(toMap(Variable::getName, Function.identity()));

        ArrayList<Byte> codeBytes = new ArrayList<>();
        for (Statement statement : statements) {
            codeBytes.addAll(statement.generateCode(cp, variableNameToIndex));
        }

        if (returnExpression != null){
            codeBytes.addAll(returnExpression.generateCode(cp, variableNameToIndex));
        }
        codeBytes.addAll(returnType.returnValue());

        int codeLength = codeBytes.size();
        short exceptionTableLength = 0;
        short attributesCount = 0;
        int attributeLength = 2 + 2 + 4 + codeLength + 2 + 2 + exceptionTableLength + attributesCount;
        ByteBuffer bb = ByteBuffer.allocate(attributeLength + 6);
        bb.putShort(cp.codeEntry.index);
        bb.putInt(attributeLength);

        bb.putShort(Short.MAX_VALUE);     // max_stack
        bb.putShort(numLocalVariables);       // max_locals
        bb.putInt(codeLength);
        bb.put(Bytes.toArray(codeBytes));
        bb.putShort(exceptionTableLength);
        bb.putShort(attributesCount);
        return bb.array();
    }

    public void addConstantPoolEntries(ConstantPool cp) {
        statements.forEach(s -> s.addConstantPoolEntries(cp));
    }

    public boolean isStatic() {
        return this instanceof MainMethodDeclaration;
    }

    public int getAccessFlags() {
        int accessFlags = 0;
        accessFlags |= 0x0001;  // public
        if (isStatic()) {
            accessFlags |= 0x0008;
        }
        if (isSynchronized) {
            accessFlags |= 0x0020;
        }
        return accessFlags;
    }
}
