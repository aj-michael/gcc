package edu.rosehulman.minijavac;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import edu.rosehulman.minijavac.generated.Lexer;
import edu.rosehulman.minijavac.generated.Parser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(value = Parameterized.class)
public class ParserTest {

    private static final String INPUT_FOLDER = "src/test/resources/parser/java";
    private static final String OUTPUT_FOLDER = "src/test/resources/parser/out";
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

    public ParserTest(File testFile, File outputFile) {
        this.testFile = testFile;
        this.outputFile = outputFile;
    }

    @Test
    public void test() throws Exception {
        Lexer lexer = new Lexer(new FileReader(testFile));
        Parser parser = new Parser(lexer);
        parser.parseProgram();
        String expectedOutput = Files.toString(outputFile, Charsets.UTF_8);
        String actualOutput = parser.getParseLog();
        assertEquals(expectedOutput, actualOutput);
    }
}
