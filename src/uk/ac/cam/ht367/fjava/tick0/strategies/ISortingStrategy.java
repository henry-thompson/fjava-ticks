package uk.ac.cam.ht367.fjava.tick0.strategies;

import java.io.IOException;

/**
 * Created by Henry Thompson on 14/09/2016.
 */
public interface ISortingStrategy {
    void sort(String filepath1, String filepath2) throws IOException;
}