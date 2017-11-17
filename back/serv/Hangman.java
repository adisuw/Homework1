package back.serv;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Hangman {

    private BufferedReader br;
    private final List<String> lists = new ArrayList<>();
    private String[] words;
    public String randomWord = "";
    public static String asterisk;
    private  int count = 0;
    private static boolean again = false;
    public Hangman() {
        readFiles();
        randomWord = getRandomWord();
        asterisk = setAsterisk(randomWord);
    }
    public String getAsterisk(){
        return asterisk;
    }
    public void setCount(int count){
        this.count = count;
    }
    public int getCount(){
        return count;
    }
    public void readFiles() {

        try {
            br = new BufferedReader(new FileReader("words.txt"));
            while (br.readLine() != null) {
                lists.add(br.readLine());
            }
            words = lists.toArray(new String[0]);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Hangman.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Hangman.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException ex) {
            }
        }

    }

    public String setAsterisk(String selected) {
        return new String(new char[selected.length()]).replace("\0", "_");
    }

    public String getRandomWord() {
        return words[(int) (Math.random() * words.length)].toLowerCase();
    }

    public static void main(String[] args) {
            sendData();
    }
    public static void sendData(){
        int s;
       Hangman hang = new Hangman();
        System.out.println("The word is: " + hang.randomWord);
        try (Scanner sc = new Scanner(System.in)) {
            
            while (hang.getCount() < 6 && asterisk.contains("_")) {
                
                System.out.println("Guess any letter in the word");
                System.out.println(asterisk);
                String guess = sc.next();
                //System.out.println("Your letter is: " + guess.charAt(0));
                s = hang.hang(guess.toLowerCase().charAt(0));
                switch (s) {
                    case 1:
                        System.out.println("Wrong guess, try again");
                        break;
                    case 2:
                        System.out.println("You loose! The word was: " + hang.randomWord);
                        break;
                    case 3:
                        System.out.println("Congrats! You won! The word was " + hang.randomWord);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    public int hang(char guess) {
        String wordFound = "";
        int state = 0;
        for (int i = 0; i < randomWord.length(); i++) {
            if (randomWord.charAt(i) == guess) {
                wordFound += guess;
            } else if (asterisk.charAt(i) != '_') {
                wordFound += randomWord.charAt(i);
            } else {
                wordFound += "_";
            }
        }

        if (asterisk.equals(wordFound)) {
            count++;
            //System.out.println("Wrong guess, try again");
            state = 1;
            if (count == 6) {
                state = 2;
                //System.out.println("You loose! The word was: " + randomWord);
                again = true;
            }
        } else {
            asterisk = wordFound;
        }
        if (asterisk.equals(randomWord)) {
            //System.out.println("Congrats! You won! The word was " + randomWord);
            state = 3;
            again = true;
        }
        return state;
    }
}
