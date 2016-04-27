package edu.rosehulman.minijavac.generator;

import edu.rosehulman.minijavac.ast.ClassDeclaration;
import edu.rosehulman.minijavac.ast.Program;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

public class CodeGenerator {
    private final Program program;

    public CodeGenerator(Program program) {
        this.program = program;
    }

    public Map<String, byte[]> generate() throws IOException {
        Map<String, byte[]> compiledFiles = new HashMap<>();
        for (ClassDeclaration cd : program.getClassDeclarations()) {
            compiledFiles.put(cd.name, compile(cd));
        }
        return compiledFiles;
    }

    byte[] compile(ClassDeclaration cd) throws IOException {
        ConstantPool cp = cd.getConstantPool();
        byte[] header = ByteBuffer.allocate(8)
                .putInt(0xCAFEBABE)
                .putInt(0x00000034)
                .array();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        out.write(header);
        out.write(ByteBuffer.allocate(2).putShort((short) cp.entries.size()).array());
        for (ConstantPoolEntry entry : cp.entries) {
            out.write(entry.getBytes());
        }
        return out.toByteArray();
    }
}
