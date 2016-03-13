package edu.rosehulman.minijavac;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableList;
import com.google.common.io.Files;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(value = Parameterized.class)
public class LexerTest {

    private static final String INPUT_FOLDER = "src/test/resources/lexer/java";
    private static final String OUTPUT_FOLDER = "src/test/resources/lexer/out";

    static class DirectoryFilter implements FileFilter {

        boolean wantDirectories;

        public DirectoryFilter(boolean wantDirectories) {
            this.wantDirectories = wantDirectories;
        }

        @Override
        public boolean accept(File pathname) {
            return this.wantDirectories == pathname.isDirectory();
        }
    }

    public static List<File> findFiles(File file) {
        if(file.isDirectory()) {
            File[] files = file.listFiles(new DirectoryFilter(false));
            File[] directories = file.listFiles(new DirectoryFilter(true));
            List<File> fileList = Arrays.asList(files);
            for(File directory : directories) {
                fileList.addAll(LexerTest.findFiles(directory));
            }
            return fileList;
        } else {
            List<File> files = new ArrayList<File>(1);
            files.add(file);
            return files;
        }
    }

    @Parameterized.Parameters(name = "{0}")
    public static Collection<File[]> testCases() {
        File inputFolder = new File(INPUT_FOLDER);
        File outputFolder = new File(OUTPUT_FOLDER);

        List<File> inputFiles = findFiles(inputFolder);
        List<File> outputFiles = findFiles(outputFolder);

        Collections.sort(inputFiles);
        Collections.sort(outputFiles);

        if(inputFiles.size() != outputFiles.size()) {
            return null;
        }

        List<File[]> fileCollection = new ArrayList<>();
        for(int k = 0; k < inputFiles.size(); k++) {
            File[] filePair = new File[2];
            filePair[0] = inputFiles.get(k);
            filePair[1] = outputFiles.get(k);
            fileCollection.add(filePair);
        }

        return fileCollection;
    }

    private File testFile;
    private File outputFile;

    public LexerTest(File testFile, File outputFile) {
        this.testFile = testFile;
        this.outputFile = outputFile;
    }

    @Test
    public void test() throws IOException {
        ImmutableList<Token> tokens = new Lexer(new FileReader(testFile)).getTokens();
        String lexerOutput = "";
        String delimiter = "";
        for(Token token: tokens) {
            lexerOutput += delimiter + token;
            delimiter = "\n";
        }
        String expectedOutput = Files.toString(this.outputFile, Charsets.UTF_8).trim();
        assertEquals(expectedOutput, lexerOutput);
    }

}
