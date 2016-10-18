package uk.ac.cam.ht367.fjava.tick0.tests;

import org.junit.Test;
import uk.ac.cam.ht367.fjava.tick0.ExternalSort;
import uk.ac.cam.ht367.fjava.tick0.io.IntArrayFileFactory;
import uk.ac.cam.ht367.fjava.tick0.strategies.MergeSortStrategy;
import uk.ac.cam.ht367.fjava.tick0.strategies.QuickMergeSortStrategy;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 * Created by Henry Thompson on 09/10/2016.
 */
public class TestFromFileInputs {

    @Test
    public void testAgainstAllFiles() throws IOException {
        copyFiles();

        String[] checkSums = getCheckSums();

        for (int index = 1; index <= 17; index++) {
            String fileA = new File(DIRECTORY, "test" + index + "a.dat").getAbsolutePath();
            String fileB = new File(DIRECTORY, "test" + index + "b.dat").getAbsolutePath();

            QuickMergeSortStrategy strategy = new QuickMergeSortStrategy(new IntArrayFileFactory());
            strategy.sort(fileA, fileB);

            System.out.println(fileA + ": " + ExternalSort.checkSum(fileA).equals(checkSums[index - 1]));
        }
    }

    private String[] getCheckSums() throws FileNotFoundException {
        File checkSumSource = new File(DIRECTORY, CHECKSUM_FILE_NAME);
        ArrayList<String> lines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(checkSumSource))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (line.length() > 0) {
                    lines.add(line);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] checkSums = new String[lines.size()];

        for (int line = 0; line < lines.size(); line++) {
            // Assumes the format of each line is "{LINE} : {CHECKSUM}"
            checkSums[line] = lines.get(line).split("\\s")[2];
        }

        return checkSums;
    }

    private void copyFiles() throws IOException {
        String[] files = ORIGINAL_DIRECTORY.list();

        for (String file : files) {
            File srcFile = new File(ORIGINAL_DIRECTORY, file);
            File destFile = new File(DIRECTORY, file);

            Files.copy(srcFile.toPath(), destFile.toPath(), REPLACE_EXISTING);
        }
    }

    private static final String TEST_FILE_DIR = "C:/tick0";
    private static final String ORIGINAL_TEST_FILE_DIR = "C:/tick0-original";
    private static final File DIRECTORY = new File(TEST_FILE_DIR);
    private static final File ORIGINAL_DIRECTORY = new File(ORIGINAL_TEST_FILE_DIR);

    private static final String CHECKSUM_FILE_NAME = "checksum.txt";
}
