/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package back.serv;

/**
 *
 * @author ema
 */
public class Play {

    private final FileHandler file = new FileHandler();
    private final String randomWord = file.getRandomWord();
    private static String asterisk;
    private int count = 0;
    private String status = " ";
    private String wordFound = "";

    public Play() {
       setAsterisk(randomWord);
    }

    public String getAsterisk(){
        return asterisk;
    }
    public String getRandomWord() {
        return randomWord;
    }

    public int getCount() {
        return count;
    }

    public void setAsterisk(String s) {
        asterisk =  new String(new char[s.length()]).replace("\0", "*");
    }

    public void acceptGuess(char guess) {
        for (int i = 0; i < randomWord.length(); i++) {
            if (randomWord.charAt(i) == guess) {
                wordFound += guess;
            } else if (asterisk.charAt(i) != '*') {
                wordFound += randomWord.charAt(i);
            } else {
                wordFound += "*";
            }
        }
    }

    public String sendResult() {
        status = randomWord;
        if (asterisk.equals(wordFound)) {
            count++;
            status = "Wrong guess, try again";
            if (count == 6) {
                status = "You loose! The word was: " + randomWord;
            }
        } else {
            asterisk = wordFound;
        }
        if (asterisk.equals(randomWord)) {
            status = "Congrats! You won! The word was " + randomWord;
        }
        return status;
    }

    public String playGame(char guess) {

        String result;
        while (getCount() < 6 && asterisk.contains("*")) {
            acceptGuess(guess);
        }
        result = sendResult();
        return result;
    }
}
