package uk.ac.cam.ht367.fjava.tick0.io;

import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Created by Henry Thompson on 14/09/2016.
 */
public interface IOutputStreamFactory {
    ObjectOutputStream getOutputStream(String filepath) throws IOException;
}
