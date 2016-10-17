package uk.ac.cam.ht367.fjava.tick1;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * Reads the input from a server and continually outputs the data passed back from it.
 * Created by Henry Thompson on 13/10/2016.
 */
public class StringReceive {
    /**
     * The {@code InputStream} from which the server's messages are read in.
     */
    private final InputStream mServerInput;

    public StringReceive(InputStream serverInput) {
        mServerInput = serverInput;
    }

    /**
     * Reads the output from the server specified by the user in an infinite loop.
     * @throws IOException When some error occurs connecting to the server.
     */
    @SuppressWarnings("InfiniteLoopStatement")
    public void readServerOutputForever() throws IOException {
        while (true) {
            final byte[] bytes = new byte[1024];
            mServerInput.read(bytes);
            final String message = new String(bytes, StandardCharsets.US_ASCII);
            System.out.println(message);
        }
    }

    /**
     * This function is gross and absolutely should not be here, but since the tick tester
     * wants to readUserInputForever this class from here, it stays. *sigh*
     * @param args Expects 2 arguments: the hostname and port respectively.
     */
    public static void main(String[] args) {
        try {
            final Socket socket = new ProgramArgumentsParser().getSocketFromArguments(args);
            new StringReceive(socket.getInputStream()).readServerOutputForever();
        } catch (IOException | IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }
}
