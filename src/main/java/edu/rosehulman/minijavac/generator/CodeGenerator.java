package edu.rosehulman.minijavac.generator;

import edu.rosehulman.minijavac.ast.ClassDeclaration;
import edu.rosehulman.minijavac.ast.MainMethodDeclaration;
import edu.rosehulman.minijavac.ast.MethodDeclaration;
import edu.rosehulman.minijavac.ast.Program;
import edu.rosehulman.minijavac.ast.VariableDeclaration;

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
        out.write(ByteBuffer.allocate(10).putShort((short) 1)
            .putShort(cp.classEntryMap.get(cd.name).index)
            .putShort(cp.classEntryMap.get(cd.getParentClass()).index)
            .putShort((short) 0)
            .putShort((short) cp.fieldRefEntryMap.size())
            .array());

        for (VariableDeclaration vd : cd.classVariableDeclarations) {
            out.write(ByteBuffer.allocate(8)
                .putShort((short) 1)
                .putShort(cp.utf8EntryMap.get(vd.name).index)
                .putShort(cp.utf8EntryMap.get(vd.getDescriptor()).index)
                .putShort((short) 0)
                .array());
        }

        out.write(ByteBuffer.allocate(2).putShort((short) cd.methodDeclarations.size()).array());
        for(MethodDeclaration md : cd .methodDeclarations) {
            ByteBuffer bb = ByteBuffer.allocate(8);
            if(md instanceof MainMethodDeclaration) {
                bb.putShort((short) 9);
            } else {
                bb.putShort((short) 1);
            }

            bb.putShort(cp.utf8EntryMap.get(md.name).index);
            bb.putShort(cp.utf8EntryMap.get(md.getDescriptor()).index);
            bb.putShort((short) 1);
        }

        return out.toByteArray();
    }
}
