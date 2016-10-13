package uk.ac.cam.ht367.fjava.tick0;

/**
 * Created by Henry Thompson on 09/10/2016.
 */
public class MathUtils {
    public static int roundUpDivide(int numerator, int denominator) {
        return (int) Math.ceil(((double) numerator) / denominator);
    }
}
