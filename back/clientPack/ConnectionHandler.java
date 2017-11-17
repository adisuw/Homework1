package back.clientPack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;
import javax.swing.JOptionPane;

public class ConnectionHandler {

    private final LinkedBlockingQueue<String> string = new LinkedBlockingQueue<>();
    private Socket socket;
    private String message = " ";
    private final String host;
    private BufferedReader input;
    private PrintStream print;
    private final int port;

    public ConnectionHandler(String host, int port) {
        this.host = host;
        this.port = port;
        connectToServer();
        getStreams();
    }

    public void connectToServer() {
        sendMessage("Attempting Connection");
        try {
            socket = new Socket(InetAddress.getByName(host), port);
            sendMessage("Connected to: " + socket.getInetAddress().getHostName());
        } catch (IOException ex) {
            sendMessage("Could'nt connect to thost: " + ex);
        }
    }

    public void getStreams() {
        try {
            print = new PrintStream(socket.getOutputStream());
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            sendMessage("Got socket streams");
        } catch (IOException ex) {
            sendMessage("Unable to got socket streams: " + ex);
        }
    }

    public String acceptFromServer() {
        getStreams();
        String msg = "";
        try {
            JOptionPane.showMessageDialog(null, "you are here in accept from server");
            msg = input.readLine();
            JOptionPane.showMessageDialog(null, "from server: "+msg);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return msg;
    }

    public void process(String guessedWord) throws IOException, InterruptedException {
        if (!guessedWord.equals("!")) {
            JOptionPane.showMessageDialog(null, guessedWord);
            print.print(guessedWord);
            print.flush();
        } else {
            closeConnection();
        }
    }

    public void closeConnection() {
        sendMessage("Closing connection");
        try {
            print.close();
            input.close();
            socket.close();
        } catch (IOException ex) {
            sendMessage("Couldn't close the connection: " + ex);
        }
    }

    public void sendMessage(String message) {
        System.out.println(message);
    }

    public void runClient() {

    }

}
