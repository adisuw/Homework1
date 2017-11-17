package id1212.homework1.adisu.hangmanServer.main;

import id1212.homework1.adisu.hangmanServer.net.ServerConnectionHandler;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HangmanServer {

    private static ServerSocket server;
    private static Socket client;
    private static int counter = 1;

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        try {
            server = new ServerSocket(4444);
            displayMessage("Server Started...."); 
            displayMessage("Waiting for connection");
            while (true) {
                client = server.accept();
                displayMessage("Client: "+counter+" conneccted");
                counter++;
                executor.execute(new ServerConnectionHandler(client));
            }
        } catch (IOException ex) {
            displayMessage("IOException: " + ex);

        }

    }

    private static void displayMessage(String message) {
        System.out.println(message);
    }
}
