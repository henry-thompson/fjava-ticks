package uk.ac.cam.ht367.fjava.tick0.strategies;

import uk.ac.cam.ht367.fjava.tick0.io.IIntArrayFileFactory;
import uk.ac.cam.ht367.fjava.tick0.io.IntArrayFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by Henry Thompson on 09/10/2016.
 */
public class QuickMergeSortStrategy implements ISortingStrategy {
    private final IIntArrayFileFactory mIntArrayFileFactory;

    public QuickMergeSortStrategy(IIntArrayFileFactory factory) {
        mIntArrayFileFactory = factory;
    }

    @Override
    public void sort(final String filepath1, final String filepath2) throws IOException {
        final IntArrayFile file1 = mIntArrayFileFactory.createIntArrayFile(filepath1);

        final int intCount = (int) new File(filepath1).length() / Integer.BYTES;
        int remaining = intCount;

        final int[] inMemory = new int[THREE_MEGABYTES_OF_INTS];

        while (remaining > 0) {
            final int intsToRead = remaining >= THREE_MEGABYTES_OF_INTS ? THREE_MEGABYTES_OF_INTS : remaining;

            readIntsIntoArray(inMemory, file1, intsToRead);
            Arrays.sort(inMemory);
            writeIntsToFile(inMemory, file1, intsToRead);

            remaining -= intsToRead;
        }

        file1.flushAndReset();

        if (intCount > THREE_MEGABYTES_OF_INTS) {
            new MergeSortStrategy(mIntArrayFileFactory).sort(filepath1, filepath2, THREE_MEGABYTES_OF_INTS);
        }
    }

    private void readIntsIntoArray(int[] array, IntArrayFile file, int count) throws IOException {
        for (int i = 0; i < count; i++) {
            array[i] = file.readInt();
        }
    }

    private void writeIntsToFile(int[] array, IntArrayFile file, int count) throws IOException {
        for (int i = 0; i < count; i++) {
            file.writeInt(array[i]);
        }
    }

    private static final int THREE_MEGABYTES_OF_INTS = 3 * 1024 * 1024 / Integer.BYTES;
}
