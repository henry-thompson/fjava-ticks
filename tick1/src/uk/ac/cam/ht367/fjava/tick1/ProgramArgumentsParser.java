package uk.ac.cam.ht367.fjava.tick1;

import java.io.IOException;
import java.net.Socket;

/**
 * Takes the arguments provided to the program and generates the relevant objects from it.
 * Created by Henry Thompson on 13/10/2016.
 */
public class ProgramArgumentsParser {
    /**
     * Returns a ServerArguments POJO specifying the hostname and port number provided by the user
     * in the arguments when the program is readUserInputForever.
     * @param args The arguments passed to the program by the user.
     * @return The ServerArguments POJO specifying the server hostname and port number.
     */
    public Socket getSocketFromArguments(String[] args) throws IOException, IllegalArgumentException {
        if (args.length != 2) {
            throw new IllegalArgumentException("This application requires two arguments: <machine> <port>");
        }

        final String host = args[0];
        final int port;

        try {
            port = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("This application requires two arguments: <machine> <port>");
        }

        return new Socket(host, port);
    }
}
