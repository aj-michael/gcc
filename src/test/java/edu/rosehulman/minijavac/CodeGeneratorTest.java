package edu.rosehulman.minijavac;

import com.google.common.base.Charsets;
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
import org.junit.runners.Parameterized;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(value = Parameterized.class)
public class CodeGeneratorTest {

    private static final String INPUT_FOLDER = "src/test/resources/codegenerator/java";
    private static final String OUTPUT_FOLDER = "src/test/resources/codegenerator/out";
    private static final String OUTPUT_EXTENSION = ".out";

    @Parameterized.Parameters(name = "{0}")
    public static Collection<File[]> testCases() {
        List<File[]> fileCollection = new ArrayList<>();
        for (File testFile : Files.fileTreeTraverser().children(new File(INPUT_FOLDER))) {
            File outputFile = new File(OUTPUT_FOLDER,
                    Files.getNameWithoutExtension(testFile.getName()) + OUTPUT_EXTENSION);
            if (outputFile.exists()) {
                File[] filePair = {testFile, outputFile};
                fileCollection.add(filePair);
            }
        }
        fileCollection.sort((o1, o2) -> o1[0].getName().compareTo(o2[0].getName()));
        return fileCollection;
    }

    private final File testFile;
    private final File outputFile;

    private final ByteArrayOutputStream output = new ByteArrayOutputStream();

    public CodeGeneratorTest(File testFile, File outputFile) {
        this.testFile = testFile;
        this.outputFile = outputFile;
    }

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
        Parser parser = new Parser(new Lexer(new FileReader(testFile)));
        Program program = parser.parseProgram();
        TypeChecker typeChecker = new TypeChecker();
        if (!typeChecker.isValid(program)) {
            fail("Program failed to typecheck: " + typeChecker.getTypeCheckerLog());
        }
        Map<String, byte[]> classDefinitions = new CodeGenerator(program).generate();
        ClassLoader programClassLoader = new ByteClassLoader(classDefinitions);
        Class mainClass = programClassLoader.loadClass(program.mainClassDeclaration.name);
        Method mainMethod = mainClass.getMethod("main", String[].class);
        Object[] args = new Object[1];
        args[0] = new String[0];
        mainMethod.invoke(null, args);
        String expectedOutput = Files.toString(outputFile, Charsets.UTF_8);
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
    }
}
