package uk.ac.cam.ht367.fjava.tick0;

import uk.ac.cam.ht367.fjava.tick0.io.IntArrayFileFactory;
import uk.ac.cam.ht367.fjava.tick0.strategies.MergeSortStrategy;
import uk.ac.cam.ht367.fjava.tick0.strategies.ISortingStrategy;
import uk.ac.cam.ht367.fjava.tick0.strategies.QuickMergeSortStrategy;

import java.security.MessageDigest;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.NoSuchAlgorithmException;

public class ExternalSort {

    private static final ISortingStrategy mSortStrategy = new QuickMergeSortStrategy(new IntArrayFileFactory());

    public static void sort(String filepath1, String filepath2) throws IOException {
        mSortStrategy.sort(filepath1, filepath2);
    }

    private static String byteToHex(byte b) {
        String r = Integer.toHexString(b);

        if (r.length() == 8) {
            return r.substring(6);
        }
        return r;
    }

    public static String checkSum(String f) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            DigestInputStream ds = new DigestInputStream(new FileInputStream(f), md);

            for (int i = 0; i < 5; i++) {
                ds.read();
            }

            byte[] b = new byte[512];
            while (ds.read(b) != -1);

            String computed = "";

            for (byte v : md.digest()) {
                computed += byteToHex(v);
            }

            return computed;

        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        }

        return "<error computing checksum>";
    }

    public static void main(String[] args) throws Exception {
        String filepath1 = args[0];
        String filepath2 = args[1];

        System.out.println(filepath1);
        System.out.println(filepath2);

        sort(filepath1, filepath2);

        System.out.println("The checksum is: " + checkSum(filepath1));
    }
}
