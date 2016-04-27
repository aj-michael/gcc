package edu.rosehulman.minijavac.generator;

import edu.rosehulman.minijavac.ast.ClassDeclaration;
import edu.rosehulman.minijavac.ast.Program;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.Map;

public class CodeGenerator {
    private final Program program;

    public CodeGenerator(Program program) {
        this.program = program;
    }

    public Map<String, ByteBuffer> generate() {
        for (ClassDeclaration cd : program.getClassDeclarations()) {
            compile(cd);
        }
        return null;
    }

    ByteBuffer compile(ClassDeclaration cd) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(0xCAFEBABE); // Magic number
        baos.write(0x00000034);     // Java 8 version number

        return null;
    }


}
