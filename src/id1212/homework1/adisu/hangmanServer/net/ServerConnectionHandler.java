package id1212.homework1.adisu.hangmanServer.net;

import id1212.homework1.adisu.hangmanServer.gameplay.HangmanPlay;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerConnectionHandler implements Runnable {

    private ObjectOutputStream output;
    private ObjectInputStream input;
    private final Socket client;
    private static final String MSG = "\nGuess any letter in the word\n";
    private static final String AGAIN = "To play again type the word again ";
    private int state, score = 0, remainingTry = 7;

    public ServerConnectionHandler(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        try {
            HangmanPlay play = new HangmanPlay();
            String toClient = play.getUnderline();
            
            output = new ObjectOutputStream(client.getOutputStream());
            input = new ObjectInputStream(client.getInputStream());

            //System.out.println("The word is: " + play.randomWord);
            
            output.writeObject(MSG + play.underline);
            output.flush();

            while (play.getCount() < 7 && play.underline.contains("_")) {
                String guess = (String) input.readObject();
                state = play.updateUnderline(guess.toLowerCase().charAt(0));
                switch (state) {
                    case 0:
                        output.writeObject(MSG + play.underline);
                        output.flush();
                        output.writeInt(remainingTry);
                        output.flush();
                        output.writeInt(score);
                        output.flush();
                        break;
                    case 1:
                        remainingTry -= 1;
                        output.writeObject("\nWrong guess, try again\n" + play.underline);
                        output.flush();
                        output.writeInt(remainingTry);
                        output.flush();
                        output.writeInt(score);
                        output.flush();
                        break;
                    case 2:
                        remainingTry -= 1;
                        score -= 1;
                        output.writeObject("\nYou loose! The word was: " + play.randomWord + "\n" + AGAIN);
                        output.flush();
                        output.writeInt(remainingTry);
                        output.flush();
                        output.writeInt(score);
                        output.flush();
                        break;
                    case 3:
                        score += 1;
                        output.writeObject("\nCongrats! You won! The word was " + play.randomWord + "\n" + AGAIN);
                        output.flush();
                        output.writeInt(remainingTry);
                        output.flush();
                        output.writeInt(score);
                        output.flush();
                        break;
                    default:
                        break;
                }

            }
            output.writeObject("\n" + AGAIN);
            output.flush();
            String guess = (String) input.readObject();
            if (guess.equals("again")) {
                playAgain();
            }

        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("Exception generated: " + ex);
        }

    }

    public void playAgain() {
        try {
            HangmanPlay play = new HangmanPlay();
            String toClient = play.getUnderline();
            
            output = new ObjectOutputStream(client.getOutputStream());
            input = new ObjectInputStream(client.getInputStream());

            output.writeObject(MSG + play.underline);
            output.flush();

            while (play.getCount() < 7 && play.underline.contains("_")) {
                String guess = (String) input.readObject();

                state = play.updateUnderline(guess.toLowerCase().charAt(0));
                switch (state) {
                    case 0:
                        output.writeObject(MSG + play.underline);
                        output.flush();
                        output.writeInt(remainingTry);
                        output.flush();
                        output.writeInt(score);
                        output.flush();
                        break;
                    case 1:
                        remainingTry -= 1;
                        output.writeObject("Wrong guess, try again\n" + play.underline);
                        output.flush();
                        output.writeInt(remainingTry);
                        output.flush();
                        output.writeInt(score);
                        output.flush();
                        break;
                    case 2:
                        remainingTry -= 1;
                        score -= 1;
                        output.writeObject("You loose! The word was: " + play.randomWord + "\n" + AGAIN);
                        output.flush();
                        output.writeInt(remainingTry);
                        output.flush();
                        output.writeInt(score);
                        output.flush();
                        break;
                    case 3:
                        score += 1;
                        output.writeObject("Congrats! You won! The word was " + play.randomWord + "\n" + AGAIN);
                        output.flush();
                        output.writeInt(remainingTry);
                        output.flush();
                        output.writeInt(score);
                        output.flush();
                        break;
                    default:
                        break;
                }

            }
            output.writeObject("\n" + AGAIN);
            output.flush();
            String guess = (String) input.readObject();
            if (guess.equals("again")) {
                playAgain();
            }

        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("Exception generated: " + ex);
        }
    }

}
