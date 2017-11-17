/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package back.serv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author ema
 */
public class HangmanServer {

    FileHandler file;
    private ServerSocket server;
    private final ExecutorService threadPool;
    private Socket socket;
    private static int port = 4444;
    private int clientCounter = 0;

    public HangmanServer(int port) throws IOException {
        HangmanServer.port = port;
        threadPool = Executors.newFixedThreadPool(10);
    }

    public void startServer() {
        try {
            server = new ServerSocket(port);
            message("Server Started...");
            message("Waiting for connection");
            while (true) {
                socket = server.accept();
                clientCounter++;
                message("client: " + clientCounter + " connected");
                threadPool.execute(new Handler(socket, clientCounter, this));
            }
        } catch (IOException e) {

        }

    }

    public static void message(final String message) {
        System.out.println(">" + message);
    }

    public static void main(String[] args) throws IOException {
        new HangmanServer(port).startServer();
    }

    private class Handler implements Runnable {

        private final Play play = new Play();
        private final HangmanServer server;
        private final Socket client;
        private final BufferedReader br;
        private final PrintStream print;
        private int counter = 0;
        private String guess;

        private Handler(Socket client, int counter, HangmanServer server) throws IOException {
            this.client = client;
            this.counter = counter;
            this.server = server;
            br = new BufferedReader(new InputStreamReader(client.getInputStream()));
            print = new PrintStream(client.getOutputStream());
            System.out.println("you are in handler runnable: ");
        }

        @Override
        public void run() {
            try {
                //while (true) {
                    //System.out.println("Random Word: " + play.getRandomWord());
                    //System.out.println(play.getAsterisk());
                    print.print("Random Word: " + play.getRandomWord() + ": " + play.getAsterisk());
                    print.flush();
                    //from client
                    String fromClient = br.readLine();
                    System.out.println("From client: "+fromClient);
                    //guess = play.playGame(fromClient.charAt(0));
                   // System.out.println(guess);
                    print.print("guess");
                    print.flush();
                //}
            } catch (IOException e) {

            } finally {
                closeConnection();

            }
        }

        private void closeConnection() {

            message("\nTerminating Connection\n");
            try {
                client.close();
                br.close();
                print.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }

}
