package uk.ac.cam.ht367.fjava.tick1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Responsible for taking {@code String}s from user input and writing it
 * out to the provided {@code OutputStream} line by line.
 * Created by Henry Thompson on 13/10/2016.
 */
public class StringSend {
    /**
     * The BufferedReader from which user input is read.
     */
    private final BufferedReader mUserInputReader;

    /**
     * The OutputStream to which user input is read.
     */
    private final OutputStream mOutput;

    /**
     * @param userInputReader The BufferedReader from which the input should be read.
     * @param outputStream The OutputStream to which user input should be written.
     */
    public StringSend(BufferedReader userInputReader, OutputStream outputStream) {
        mUserInputReader = userInputReader;
        mOutput = outputStream;
    }

    /**
     * Reads the user input from the command line indefinitely line by line, and writes it to the
     * provided output buffer.
     * @throws IOException If unable to read the next line from the command line.
     */
    @SuppressWarnings("InfiniteLoopStatement")
    public void readUserInputForever() throws IOException {
        while(true) {
            final byte[] message = mUserInputReader.readLine().getBytes();
            mOutput.write(message);
            mOutput.flush();
        }
    }
}