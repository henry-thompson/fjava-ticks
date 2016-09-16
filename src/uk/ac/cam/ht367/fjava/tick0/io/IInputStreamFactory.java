package uk.ac.cam.ht367.fjava.tick0.io;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * Created by Henry Thompson on 14/09/2016.
 */
public interface IInputStreamFactory {
    DataInputStream getInputStream(String path) throws IOException;
}
