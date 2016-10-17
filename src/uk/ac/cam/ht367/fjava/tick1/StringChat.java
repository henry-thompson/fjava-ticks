package uk.ac.cam.ht367.fjava.tick1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class StringChat {

    public static void main(String[] args) throws IOException {
        // Would be nicer with a DI container

        try {
            final Socket socket = new ProgramArgumentsParser().getSocketFromArguments(args);

            final StringReceive receiver = new StringReceive(socket.getInputStream());
            final StringSend sender = new StringSend(new BufferedReader(new InputStreamReader(System.in)), socket.getOutputStream());

            new ChatSession(receiver, sender).start();

        } catch (IOException | IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }
}