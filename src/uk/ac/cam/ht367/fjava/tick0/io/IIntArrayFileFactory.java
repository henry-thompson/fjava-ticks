package uk.ac.cam.ht367.fjava.tick0.io;

import uk.ac.cam.ht367.fjava.tick0.io.IntArrayFile;

import java.io.IOException;

/**
 * Created by Henry Thompson on 09/10/2016.
 */
public interface IIntArrayFileFactory {
    IntArrayFile createIntArrayFile(String filepath) throws IOException;
}
