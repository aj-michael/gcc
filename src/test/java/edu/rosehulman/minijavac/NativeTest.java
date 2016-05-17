package edu.rosehulman.minijavac;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableList;
import com.google.common.io.Files;
import edu.rosehulman.minijavac.ast.Program;
import edu.rosehulman.minijavac.generated.Lexer;
import edu.rosehulman.minijavac.generated.Parser;
import edu.rosehulman.minijavac.generator.CodeGenerator;
import edu.rosehulman.minijavac.typechecker.TypeChecker;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(value = JUnit4.class)
public class NativeTest {

    private static final String INPUT_FILEPATH = "src/test/resources/native/java/testcase16_01.java";
    private static final String OUTPUT_FILEPATH = "src/test/resources/native/out/testcase16_01.out";
    private static final String LIBRARY = "Squarer";
    private static final String LIBRARY_FILEPATH = "src/test/resources/native/jni/libSquarer.so";

    private static final File TEST_FILE = new File(INPUT_FILEPATH);
    private static final File OUTPUT_FILE = new File(OUTPUT_FILEPATH);
    private static final File LIBRARY_FILE = new File(LIBRARY_FILEPATH);

    private final ByteArrayOutputStream output = new ByteArrayOutputStream();

    @Before
    public void setUpStream() {
        System.setOut(new PrintStream(output));
    }

    @After
    public void cleanUpStream() {
        System.setOut(null);
    }

    @Test
    public void test() throws Exception {
        Parser parser = new Parser(new Lexer(new FileReader(TEST_FILE)));
        Program program = parser.parseProgram();
        TypeChecker typeChecker = new TypeChecker();
        if (!typeChecker.isValid(program)) {
            fail("Program failed to typecheck: " + typeChecker.getTypeCheckerLog());
        }
        Map<String, byte[]> classDefinitions = new CodeGenerator(program, ImmutableList.of(LIBRARY)).generate();
        ClassLoader programClassLoader = new ByteClassLoader(classDefinitions);
        Class mainClass = programClassLoader.loadClass(program.mainClassDeclaration.name);
        Method mainMethod = mainClass.getMethod("main", String[].class);
        Object[] args = new Object[1];
        args[0] = new String[0];
        mainMethod.invoke(null, args);
        String expectedOutput = Files.toString(OUTPUT_FILE, Charsets.UTF_8);
        assertEquals(expectedOutput, output.toString());
    }

    static class ByteClassLoader extends ClassLoader {
        private final Map<String, byte[]> classDefs;

        ByteClassLoader(Map<String, byte[]> classDefs) {
            super(null);    // No parent classloader.
            this.classDefs = new HashMap<>(classDefs);
        }

        @Override
        protected Class<?> findClass(final String name) throws ClassNotFoundException {
            byte[] classBytes = classDefs.remove(name);
            if (classBytes != null) {
                return defineClass(name, classBytes, 0, classBytes.length);
            }
            return super.findClass(name);
        }

        @Override
        protected String findLibrary(String libname) {
            if (libname.equals(LIBRARY)) {
                return LIBRARY_FILE.getAbsolutePath();
            } else {
                return super.findLibrary(libname);
            }
        }
    }
}
