package uk.ac.cam.ht367.fjava.tick1;

import java.io.IOException;

/**
 * A Facade encapsulating the StringReceive and StringSend to provide a clean
 * interface through which a single chat session can be encapsulated.
 */
public class ChatSession {
    /**
     * The {@code StringReceive} responsible for obtaining messages from the server and writing it to the
     * provided {@code OutputStream}.
     */
    private final StringReceive mReceiver;

    /**
     * The {@code StringSend} responsible for taking the user input and writing it out to the server.
     */
    private final StringSend mSender;

    /**
     * @param receiver The {@code StringReceive} responsible for obtaining messages from the server and
     *                 writing it to the provided {@code OutputStream}.
     * @param sender   The {@code StringSend} responsible for taking the user input and writing it out
     *                 to the server.
     */
    public ChatSession(StringReceive receiver, StringSend sender) {
        mReceiver = receiver;
        mSender = sender;
    }

    /**
     * Starts the chat session and keeps it running indefinitely.
     * @throws IOException If reading and writing to and from any of the input and output streams fails.
     */
    public void start() throws IOException {
        startSenderThread();
        mReceiver.readServerOutputForever();
    }

    /**
     * Starts a thread running the {@code StringSender} indefinitely as a daemon.
     */
    private void startSenderThread() {
        final Thread output = new Thread() {
            @Override
            public void run() {
                try {
                    mSender.readUserInputForever();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        output.setDaemon(true);
        output.start();
    }
}
