package uk.ac.cam.ht367.fjava.tick0.io;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by Henry Thompson on 14/09/2016.
 */
public class FileInputStreamFactory implements IInputStreamFactory {
    @Override
    public DataInputStream getInputStream(String filepath) throws IOException {
        return new DataInputStream(new BufferedInputStream(new FileInputStream(filepath)));
    }
}
