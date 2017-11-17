package id1212.homework1.adisu.hangmanClient.net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.JOptionPane;

/**
 *
 * @author ema
 */
public class ClientConnectionHandler {

    private ObjectOutputStream output;
    private ObjectInputStream input;
    private Socket client;
    private int port;
    private String host;

    public ClientConnectionHandler(String host, int port) {
        this.host = host;
        this.port = port;
        try {
            client = new Socket(host, port);
            output = new ObjectOutputStream(client.getOutputStream());
            input = new ObjectInputStream(client.getInputStream());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Unable to got I/O streams: " + ex);
        }

    }

    public void process(String guess) throws IOException {
        try {
            String s = guess;
            output.writeObject(s);
            output.flush();
        } catch (IOException ex) {
            message("Oops...IOException: " + ex);
        }
    }

    public int getScor() throws IOException {
        return input.readInt();
    }

    public int getRemainingTries() throws IOException {
        return input.readInt();
    }

    public String getFromServer() throws IOException, ClassNotFoundException {
        return (String) input.readObject();
    }

    private static void message(String msg) {
        System.out.println(">" + msg);
    }

    public void closeConnection() {
        try {
            message("Closing connection...");
            output.close();
            input.close();
            client.close();
        } catch (IOException ex) {
            message("Unable to close...");
        }

    }

}
