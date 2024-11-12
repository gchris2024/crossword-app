///* *****************************************
// * CSCI 205 - Software Engineering and Design
// * Fall 2024
// * Instructor: Prof. Lily
// *
// * Name: Chris Garcia
// * Section: 01 - 09:00
// * Date: 11/11/2024
// * Time: 10:33 AM
// *
// * Project: csci205_final_project
// * Package: PACKAGE_NAME
// * Class: WordGenerator
// *
// * Description:
// *
// * ****************************************
// */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Reads a txt file to store words and their corresponding hints into a {@link Map}
 * @author Chris
 */
public class WordDatabaseGenerator {

    public static void main(String[] args) {

        Map<String, String> wordsAndHints = new HashMap<String, String>();

        // Use a try-with-resources block to get an InputStream
        try (InputStream inStream = WordDatabaseGenerator.class.getResourceAsStream("/list-of-words-and-hints.txt")) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inStream));

            // Read a line at a time
            String sLine = null;
            while ((sLine = reader.readLine()) != null) {
                String[] words = sLine.split(":");
                String word = words[0].strip();
                String hint = words[1].strip();
                wordsAndHints.put(word, hint);
            }
        }
        catch (IOException e) {
            System.out.println(e);
        }

        // For testing
        wordsAndHints.forEach((word, hint) -> System.out.println(word + " : " + hint));

    }
}