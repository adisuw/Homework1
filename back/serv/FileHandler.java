package back.serv;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ema
 */
public class FileHandler {
    
    private BufferedReader reader;
    private final List<String> wordLists = new ArrayList<>();
    private String[] wordArrays;

    public FileHandler(){
        readFiles();
        getRandomWord();
    }
    public String getRandomWord() {
        return wordArrays[(int) (Math.random() * wordArrays.length)].toLowerCase();
    }
    public void readFiles() {

        try {
            reader = new BufferedReader(new FileReader("words.txt"));
            while (reader.readLine() != null) {
                wordLists.add(reader.readLine());
            }
            wordArrays = wordLists.toArray(new String[0]);
        } catch (FileNotFoundException ex) {
            displayMessage("File not found: "+ex);
        } catch (IOException ex) {
            displayMessage("IOException: "+ex);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException ex) {
                displayMessage("IOException: "+ex);
            }
        }

    }

    private void displayMessage(String message) {
       System.out.println(message);
    }
}
