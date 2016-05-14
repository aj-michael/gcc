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
                .putInt(0xCAFEBABE)     // Magic number
                .putInt(0x00000031)     // java version 5
                .array();

        ByteArrayOutputStream fields = new ByteArrayOutputStream();
        for (int k = 0; k < cd.classVariableDeclarations.size(); k++) {
            VariableDeclaration vd = cd.classVariableDeclarations.get(k);
            fields.write(ByteBuffer.allocate(8)
                .putShort((short) 1)
                .putShort(cp.utf8EntryMap.get(vd.name).index)
                .putShort(cp.utf8EntryMap.get(vd.getDescriptor()).index)
                .putShort((short) 0)
                .array());
        }

        ByteArrayOutputStream methods = new ByteArrayOutputStream();
        methods.write(ByteBuffer.allocate(2).putShort((short) (1  + cd.methodDeclarations.size())).array());

        // Constructor
        {
            ByteBuffer bb = ByteBuffer.allocate(31);
            bb.putShort((short) 0); // access_flags
            bb.putShort(cp.constructorNameEntry.index); // name index
            bb.putShort(cp.constructorDescriptorEntry.index);   // descriptor index
            bb.putShort((short) 1); // attributes count
            // Code attribute
            bb.putShort(cp.codeEntry.index);
            bb.putInt(2+2+4+2+2+1+2+2);
            bb.putShort((short) 1);
            bb.putShort((short) 1);
            bb.putInt(5);   //code_length
            // Start instructions
            bb.putShort((short) 0x2AB7);

            bb.putShort(cp.methodRefEntry(cd.getParentClass(), "<init>", "()V").index);
            bb.put((byte) 0xB1);
            // End instructions
            // Exception table length
            bb.putShort((short) 0);
            // attributes_count
            bb.putShort((short) 0);
            methods.write(bb.array());
        }


        // Class methods
        for (MethodDeclaration md : cd.methodDeclarations) {
            ByteBuffer bb = ByteBuffer.allocate(8);
            if(md instanceof MainMethodDeclaration) {
                bb.putShort((short) 9);
            } else {
                bb.putShort((short) 1);
            }
            bb.putShort(cp.utf8EntryMap.get(md.name).index); // name_index
            bb.putShort(cp.utf8EntryMap.get(md.getDescriptor()).index); // descriptor_index

            bb.putShort((short) 1);     // attributes_count
            methods.write(bb.array());

            // Code attribute
            methods.write(md.getBytes(cp));
        }

        // attributes_count
        ByteArrayOutputStream attributes = new ByteArrayOutputStream();
        attributes.write(ByteBuffer.allocate(2).putShort((short) 0).array());

        ByteArrayOutputStream finalOut = new ByteArrayOutputStream();
        finalOut.write(header);

        finalOut.write(ByteBuffer.allocate(2).putShort(cp.index).array());
        for (ConstantPoolEntry entry : cp.entries) {
            finalOut.write(entry.getBytes());
        }

        finalOut.write(ByteBuffer.allocate(10)
                .putShort((short) 0x0021)   // Class access level
                .putShort(cp.classEntryMap.get(cd.name).index)
                .putShort(cp.classEntryMap.get(cd.getParentClass()).index)
                .putShort((short) 0)
                .putShort((short) cd.classVariableDeclarations.size())
                .array());
        finalOut.write(fields.toByteArray());
        finalOut.write(methods.toByteArray());
        finalOut.write(attributes.toByteArray());
        return finalOut.toByteArray();
    }
}
