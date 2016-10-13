package uk.ac.cam.ht367.fjava.tick0.io;

import java.io.*;

/**
 * Created by Henry Thompson on 07/10/2016.
 */
public class IntArrayFile {
    private final String mFilepath;
    private final DataInputStream mInputStream;
    private DataOutputStream mOutputStream;

    public IntArrayFile(String filepath) throws IOException {
        mFilepath = filepath;
        mInputStream = createDataInputStream();
        mOutputStream = createDataOutputStream();
    }

    public int readInt() throws IOException {
        return mInputStream.readInt();
    }

    public void writeInt(int value) throws IOException {
        mOutputStream.writeInt(value);
    }

    public DataInputStream createDataInputStream() throws IOException {
        RandomAccessFile file = new RandomAccessFile(mFilepath, "rw");
        return new DataInputStream(new BufferedInputStream(new FileInputStream(file.getFD())));
    }

    private DataOutputStream createDataOutputStream() throws IOException {
        RandomAccessFile file = new RandomAccessFile(mFilepath, "rw");
        return new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file.getFD())));
    }

    public void flushAndReset() throws IOException {
        mOutputStream.flush();
        mOutputStream = createDataOutputStream();
    }

    public int available() throws IOException {
        return mInputStream.available();
    }
}
