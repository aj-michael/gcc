package edu.rosehulman.minijavac;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableList;
import com.google.common.io.Files;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(value = Parameterized.class)
public class LexerTest {

    private static final String INPUT_FOLDER = "src/test/resources/lexer/java";
    private static final String OUTPUT_FOLDER = "src/test/resources/lexer/out";
    private static final String OUTPUT_EXTENSION = ".out";

    @Parameterized.Parameters(name = "{0}")
    public static Collection<File[]> testCases() {
        List<File[]> fileCollection = new ArrayList<>();
        for (File testFile : Files.fileTreeTraverser().children(new File(INPUT_FOLDER))) {
            File outputFile = new File(OUTPUT_FOLDER,
                    Files.getNameWithoutExtension(testFile.getName()) + OUTPUT_EXTENSION);
            File[] filePair = {testFile, outputFile};
            fileCollection.add(filePair);
        }
        return fileCollection;
    }

    private final File testFile;
    private final File outputFile;

    public LexerTest(File testFile, File outputFile) {
        this.testFile = testFile;
        this.outputFile = outputFile;
    }

    @Test
    public void test() throws IOException {
        ImmutableList<Token> tokens = new Lexer(new FileReader(testFile)).getTokens();
        List<String> expectedLines = Files.readLines(outputFile, Charsets.UTF_8);
        assertEquals(expectedLines.size(), tokens.size());
        for (int i = 0; i < expectedLines.size(); i++) {
            assertEquals(expectedLines.get(i), tokens.get(i).toString());
        }
    }

}
