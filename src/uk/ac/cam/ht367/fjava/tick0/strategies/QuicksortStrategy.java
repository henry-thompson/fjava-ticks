package uk.ac.cam.ht367.fjava.tick0.strategies;

import uk.ac.cam.ht367.fjava.tick0.io.IInputStreamFactory;
import uk.ac.cam.ht367.fjava.tick0.io.IOutputStreamFactory;

import java.io.*;


/**
 * Strategy for sorting by applying Counting Sort.
 */
public class QuicksortStrategy implements ISortingStrategy {
    private final IInputStreamFactory mInputFactory;
    private final IOutputStreamFactory mOutputFactory;

    private final int[] mInMemory = new int[10 * 1024 * 1024 / 4];

    public QuicksortStrategy(IInputStreamFactory input, IOutputStreamFactory output) {
        mInputFactory = input;
        mOutputFactory = output;
    }

    @Override
    public void sort(String filepath1, String filepath2) throws IOException {
        DataInputStream input1 = mInputFactory.getInputStream(filepath1);
        ObjectOutputStream output1 = mOutputFactory.getOutputStream(filepath1);

        DataInputStream input2 = mInputFactory.getInputStream(filepath2);
        ObjectOutputStream output2 = mOutputFactory.getOutputStream(filepath2);

        int count = 0;

        while (count < mInMemory.length) {
            mInMemory[count] = input1.readInt();
            count++;
        }

        quickSort(0, count);
        output2.writeObject(mInMemory);
    }

    private void quickSort(int regionStart, int regionEnd) {
        int start = regionStart;
        int end = regionEnd - 1;
        int pivot = mInMemory[(int) (start + Math.random() * (end - start))];

        while (start < end) {
            while (mInMemory[start] < pivot) {
                start++;
            }

            while (mInMemory[end] > pivot) {
                end--;
            }

            if (start < end) {
                // Swap the elements
                int temp = mInMemory[start];
                mInMemory[start] = mInMemory[end];
                mInMemory[end] = temp;
            }
        }

        if (start > regionStart) {
            quickSort(regionStart, start);
        }

        if (end < regionEnd - 1) {
            quickSort(end, regionEnd - 1);
        }
    }
}