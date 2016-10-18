package uk.ac.cam.ht367.fjava.tick0.strategies;

import uk.ac.cam.ht367.fjava.tick0.MathUtils;
import uk.ac.cam.ht367.fjava.tick0.io.IIntArrayFileFactory;
import uk.ac.cam.ht367.fjava.tick0.io.IntArrayFile;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;

/**
 * Created by Henry Thompson on 06/10/2016.
 */
public class MergeSortStrategy implements ISortingStrategy {
    private IIntArrayFileFactory mIntArrayFileFactory;

    public MergeSortStrategy(IIntArrayFileFactory factory) {
        mIntArrayFileFactory = factory;
    }

    @Override
    public void sort(String filepath1, String filepath2) throws IOException {
        sort(filepath1, filepath2, 1);
    }

    public void sort(String filepath1, String filepath2, int initialChunkLength) throws IOException {
        final IntArrayFile file1 = mIntArrayFileFactory.createIntArrayFile(filepath1);
        final IntArrayFile file2 = mIntArrayFileFactory.createIntArrayFile(filepath2);

        int intCount = (int) new File(filepath1).length() / Integer.BYTES;

        if (intCount == 0) {
            return;
        }

        IntArrayFile inputFile = file1;
        IntArrayFile outputFile = file2;

        while (initialChunkLength < intCount) {
            int chunkLengthAsBytes = initialChunkLength * Integer.BYTES;

            // The number of chunks in the file
            int chunks = MathUtils.roundUpDivide(intCount, initialChunkLength);
            boolean hasTail = chunks % 2 != 0;

            if (hasTail) {
                // The last chunk cannot be merged as there are an odd number of chunks. It will be copied over separately.
                chunks--;
            }

            DataInputStream inputA = inputFile.createDataInputStream();
            DataInputStream inputB = inputFile.createDataInputStream();

            // Skip to the start of the middle chunk
            int middleChunkByte = chunks / 2 * chunkLengthAsBytes;
            inputB.skipBytes(middleChunkByte);

            for (int merge = 0; merge < chunks; merge += 2) {
                int rightChunkLength = initialChunkLength;

                if (!hasTail && merge == chunks - 2) {
                    // The final chunk could be not the length of the full chunk length
                    rightChunkLength = intCount - (initialChunkLength * (merge + 1));
                }

                merge(inputA, inputB, outputFile, initialChunkLength, rightChunkLength);
            }

            // Any tail needs to be copied over
            copyRemainder(inputB, outputFile);
            initialChunkLength *= 2;

            if (inputFile == file1) {
                inputFile = file2;
                outputFile = file1;
            } else {
                inputFile = file1;
                outputFile = file2;
            }
        }

        // We need the output to be in file 1. If the output is set to file1,
        // since the inputs and outputs were swapped at the end of the while loop
        // above regardless, it means the last used outputFile was in fact file2.
        if (outputFile == file1) {
            copy(file2, file1);
        }
    }

    private void merge(DataInputStream inputA, DataInputStream inputB, IntArrayFile outputFile, int chunkALength, int chunkBLength) throws IOException {
        // Indices relative to the start of the current chunks being compared in array1 and array2 respectively
        int indexA = 0;
        int indexB = 0;

        // Read the first integers. This is guaranteed to work because we only ever call merge if
        // the chunk length is strictly smaller than the int count, and if any numbers exist at
        // all in the file - hence, the input streams always have integers to read.
        int intA = inputA.readInt();
        int intB = inputB.readInt();

        // Merge the items by comparing the values of the elements pointed to by the indices above
        while (indexA < chunkALength && indexB < chunkBLength) {
            if (intA < intB) {
                outputFile.writeInt(intA);
                indexA++;

                if (indexA < chunkALength) {
                    intA = inputA.readInt();
                }

            } else {
                outputFile.writeInt(intB);
                indexB++;

                if (indexB < chunkBLength) {
                    intB = inputB.readInt();
                }
            }
        }

        // Output the remaining integers.
        // Lots of code duplication here but since its dealing with lots of local
        // variables extracting duplicated logic is also messy.

        while (indexA < chunkALength) {
            outputFile.writeInt(intA);
            indexA++;

            if (indexA < chunkALength) {
                intA = inputA.readInt();
            }
        }

        while (indexB < chunkBLength) {
            outputFile.writeInt(intB);
            indexB++;

            if (indexB < chunkBLength) {
                intB = inputB.readInt();
            }
        }
    }

    private void copy(IntArrayFile source, IntArrayFile destination) throws IOException {
        while (source.available() > 0) {
            destination.writeInt(source.readInt());
        }

        destination.flushAndReset();
    }

    private void copyRemainder(DataInputStream source, IntArrayFile destination) throws IOException {
        while (source.available() > 0) {
            destination.writeInt(source.readInt());
        }

        destination.flushAndReset();
    }
}