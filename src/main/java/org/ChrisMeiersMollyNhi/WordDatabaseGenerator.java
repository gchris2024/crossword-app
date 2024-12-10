/* *****************************************
 * CSCI 205 - Software Engineering and Design
 * Fall 2024
 * Instructor: Prof. Lily
 *
 * Name: Molly Yoder
 * Section: 10am
 * Date: 12/9/2024
 * Time: 1:09 PM
 *
 * Project: csci205_final_project
 * Package: org.ChrisMeiersMollyNhi
 * Class: WordDatabaseGenerator
 *
 * Description: Makes a database of words
 *
 * ****************************************
 */

package org.ChrisMeiersMollyNhi;

import java.io.*;
import java.util.*;

/**
 * Reads a txt file to store words and their corresponding hints into a {@link Map}
 * @author Chris
 */
public class WordDatabaseGenerator {

    /** Random number generator */
    private Random rng = new Random();

    /** Main map of words and hints */
    private HashMap<String, String> wordsAndHints;

    public WordDatabaseGenerator() {
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

//        System.out.println("Total entries in map: " + wordsAndHints.size());

    }
    /**
     * Returns a random word from the database
     * @author Chris
     */
    public String returnRandomWord() {
        List<String> keys = new ArrayList<String>(wordsAndHints.keySet());
        return keys.get(rng.nextInt(wordsAndHints.size()));
    }



}