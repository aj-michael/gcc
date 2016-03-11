package edu.rosehulman.minijavac;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RunWith(value = Parameterized.class)
public class LexerFileTest {

    public static final String INPUT_FOLDER = "src/test/resources/lexer/java";
    public static final String OUTPUT_FOLDER = "src/test/resources/lexer/out";

    public static class DirectoryFilter implements FileFilter {

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
                fileList.addAll(LexerFileTest.findFiles(directory));
            }
            return fileList;
        } else {
            List<File> files = new ArrayList<File>(1);
            files.add(file);
            return files;
        }
    }

    @Parameterized.Parameters(name = "{0}")
    public static Collection<Object[]> testCases() {
        File inputFolder = new File(INPUT_FOLDER);
        File outputFolder = new File(OUTPUT_FOLDER);

        List<File> inputFiles = LexerFileTest.findFiles(inputFolder);
        List<File> outputFiles = LexerFileTest.findFiles(outputFolder);

        Collections.sort(inputFiles);
        Collections.sort(outputFiles);

        if(inputFiles.size() != outputFiles.size()) {
            return null;
        }

        ArrayList<Object[]> fileCollection = new ArrayList<Object[]>();
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

    public LexerFileTest(File testFile, File outputFile) {
        this.testFile = testFile;
        this.outputFile = outputFile;
    }

    @Test
    public void test() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream systemOut = new PrintStream(baos);
        PrintStream oldSystemOut = System.out;
        System.setOut(systemOut);

        String[] args = {this.testFile.getAbsolutePath()};
        Main.main(args);

        System.out.flush();
        System.setOut(oldSystemOut);

        String lexerOutput = baos.toString();
        String outputText = Files.toString(this.outputFile, Charsets.UTF_8).trim();

        Assert.assertEquals("Testing\nInput file: " + this.testFile.getAbsolutePath() + "\nOutput File: " + this.outputFile.getAbsolutePath(), outputText, lexerOutput);
    }

}
