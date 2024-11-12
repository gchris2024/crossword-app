package org.ChrisMeiersMollyNhi;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Reads a txt file to store words and their corresponding hints into a {@link Map}
 * @author Chris
 */
public class WordDatabaseGenerator {

    // Main map
    private HashMap<String, String> wordsAndHints;

    public WordDatabaseGenerator() {}

    /**
     * Reads a text file. Separates words and hints with a colon (:).
     * @author Chris
     */
    public void generateWordDatabase() {

        this.wordsAndHints = new HashMap<String, String>();

        // Use a try-with-resources block to get an InputStream
        try (InputStream inStream = WordDatabaseGenerator.class.getResourceAsStream("/long-list-of-words-and-hints.txt")) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inStream));

            // Read a line at a time
            String sLine = null;
            while ((sLine = reader.readLine()) != null) {
                String[] words = sLine.split(":", 2);
                String word = words[0].strip();
                String hint = words[1].strip();
                wordsAndHints.put(word, hint);
            }
        }
        catch (IOException e) {
            System.out.println(e);
        }

        System.out.println("Total entries in map: " + wordsAndHints.size());

    }
}