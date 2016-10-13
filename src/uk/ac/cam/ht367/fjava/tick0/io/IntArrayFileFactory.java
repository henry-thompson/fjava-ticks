package uk.ac.cam.ht367.fjava.tick0.io;

import java.io.IOException;

/**
 * Created by Henry Thompson on 09/10/2016.
 */
public class IntArrayFileFactory implements IIntArrayFileFactory {
    @Override
    public IntArrayFile createIntArrayFile(String filepath) throws IOException {
        return new IntArrayFile(filepath);
    }
}
