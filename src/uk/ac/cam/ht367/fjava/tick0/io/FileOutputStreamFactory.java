package uk.ac.cam.ht367.fjava.tick0.io;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Created by Henry Thompson on 14/09/2016.
 */
public class FileOutputStreamFactory implements IOutputStreamFactory {
    @Override
    public ObjectOutputStream getOutputStream(String filepath) throws IOException {
        return new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(filepath)));
    }
}
