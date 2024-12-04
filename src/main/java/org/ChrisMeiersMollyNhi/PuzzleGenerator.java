/* *****************************************
 * CSCI 205 - Software Engineering and Design
 * Fall 2024
 * Instructor: Prof. Lily
 *
 * Name: Molly Yoder
 * Section: 10am
 * Date: 11/14/2024
 * Time: 1:09 PM
 *
 * Project: csci205_final_project
 * Package: org.ChrisMeiersMollyNhi
 * Class: PuzzleGenerator
 *
 * Description:
 *
 * ****************************************
 */
package org.ChrisMeiersMollyNhi;

import java.util.ArrayList;
import java.util.Arrays;

public class PuzzleGenerator {

    /** ArrayList of words from which to create the puzzle */
    private ArrayList<String> words;

    /** The number of rows available to place letters within */
    private int numRows;

    /** The number of columns available to place letters within */
    private int numCols;

    /** An ArrayList of the words placed vertically */
    private ArrayList<String> verticalWords;

    /** An ArrayList of the words placed horizontally */
    private ArrayList<String> horizontalWords;

    /**
     * Initialize list of words and size of puzzle grid
     *
     * @param words the list of words from which to create the puzzle
     */
    public PuzzleGenerator(ArrayList<String> words){
        this.words = words;
        this.numRows = 20; // Temporary value for initial development
        this.numCols = 20; // Temporary value for initial development
        this.verticalWords = new ArrayList<>(); // Initialize verticalWords
        this.horizontalWords = new ArrayList<>(); // Initialize horizontalWords
    }

    /**
     * Validate that a crossword puzzle can be made from the word list
     *
     * @return a boolean value indicating if a puzzle is possible
     */
    public boolean isValidWordList(){
        // Check that every word will have an intersection somewhere
        boolean hasMatch = false;
        for(int i=0; i<words.size()-1; i++){
            String word = words.get(i);
            char[] chars = word.toCharArray();
            for(int j=i+1; j<words.size(); j++){
                String otherWord = words.get(j);
                if(!word.equals(otherWord)){ // Do not want to compare words to themselves
                    char[] otherChars = otherWord.toCharArray();

                    // Find matching characters
                    for(char c : chars){
                        for(char o : otherChars){
                            if(c == o){
                                hasMatch = true;
                            }
                        }
                    }
                    if(!hasMatch){
                        return false;
                    }
                }
            }
        }
        return hasMatch;
    }

    /**
     * Generate the crossword puzzle from the given word list,
     * assuming that the word list is valid
     *
     * @return A 2D ArrayList representing the crossword puzzle,
     *          where each item is either a letter or a blank spot
     */
    public char[][] generate(){
        char[][] crossword = new char[numRows][numCols];

        // Place first word in center of puzzle
        placeFirstWord(crossword);

        // Place the rest of the words on the puzzle
        for(int i=1; i< words.size(); i++){
            String word = words.get(i);
            char[] letters = word.toCharArray();

            boolean placed = false;
            for(int row = 0; row < numRows; row++){
                for(int col = 0; col < numCols; col++){
                    for(char letter : letters){
                        // Check if letter in puzzle space matches a letter in the word to place
                        // and confirm that the space is available as an intersection point
                        if(crossword[row][col] == letter && validateOpen(crossword,row,col) && wordFits(crossword, row, col, word) && !placed){
                            placeOtherWords(letter, word, crossword, row, col);
                            placed = true;
                        }
                    }
                }
            }
        }

        return crossword;
    }

    /**
     * Place a word on the partially complete puzzle grid
     *
     * @param letter the letter which will be used to connect the new word with one already on the grid
     * @param word the word to place on the grid
     * @param crossword the puzzle grid which is edited when a new word is placed
     * @param row the row index of the intersection point between words
     * @param col the column index of the intersection point between words
     */
    private void placeOtherWords(char letter, String word, char[][] crossword, int row, int col) {
        char[] lettersToPlace = word.toCharArray();
        int idx = -1;
        for(int i=0; i<lettersToPlace.length; i++){
            if(lettersToPlace[i] == letter){
                idx = i;
            }
        }
        // Pick direction
        // and fill in word in appropriate direction
        if((crossword[row -1][col]=='\u0000' && crossword[row +1][col]=='\u0000' )){
            // Place word vertical
            for(int n = idx; n >0; n--){ // first part of word
                crossword[row -n][col] = lettersToPlace[idx-n];
            }
            for(int n = 1; n<lettersToPlace.length-idx; n++){ // second part of word
                crossword[row +n][col] = lettersToPlace[idx+n];
            }

            // Add word to list of words
            this.verticalWords.add(word);
        }
        if((crossword[row][col -1]=='\u0000' && crossword[row][col +1]=='\u0000' )){
            // Place word horizontal
            for(int n = idx; n >0; n--){ // first part of word
                crossword[row][col -n] = lettersToPlace[idx-n];
            }
            for(int n = 1; n<lettersToPlace.length-idx; n++){ // second part of word
                crossword[row][col +n] = lettersToPlace[idx+n];
            }

            // Add word to list of words
            this.horizontalWords.add(word);
        }
    }

    /**
     * Place the first word in the center of the puzzle
     *
     * @param crossword the crossword puzzle grid
     */
    private void placeFirstWord(char[][] crossword) {
        String firstWord = words.getFirst();
        this.horizontalWords.add(firstWord);
        char[] chars = firstWord.toCharArray();
        int row = numRows/2;
        int charIdx = 0;
        for(int col=(numCols/2)-(chars.length/2); col<(numCols/2)-(chars.length/2)+chars.length; col++){
            char c = chars[charIdx];
            crossword[row][col] = c;
            charIdx++;
        }

    }

    /**
     * Validate if an existing letter can be used as a point of intersection for a new word
     *
     * @param crossword the incomplete crossword puzzle
     * @param row the row of the letter which would be the intersection
     * @param col the column of the letter which would be the intersection
     * @return a boolean value indicating whether the space can be used in another word
     */
    public boolean validateOpen(char[][] crossword, int row, int col){
        boolean open = true;
        // Validate that intersection point  is open
        if((crossword[row-1][col]!='\u0000' && crossword[row][col-1]!='\u0000' )||
                (crossword[row+1][col]!='\u0000' && crossword[row][col+1]!='\u0000')||
                (crossword[row-1][col]!='\u0000' && crossword[row][col+1]!='\u0000' )||
                (crossword[row+1][col]!='\u0000' && crossword[row][col-1]!='\u0000' )
        ) {
                open = false;
        }
        return open;
    }

    /**
     * Validates the word can fit without intersecting any words other than what it is supposed to
     *
     * @param crossword the puzzle grid
     * @param row the row index of the planned intersection
     * @param col the column index of the planned intersection
     * @param word the word which is to be placed
     * @return a boolean value indicating if the word can be placed
     */
    public boolean wordFits(char[][] crossword, int row, int col, String word){
        boolean fits = true;

        // Check other surrounding spaces to ensure that word will fit
        char letter = crossword[row][col];
        int letterIdx = word.indexOf(letter);
        char[] lettersToPlace = word.toCharArray();
        int beginLength = letterIdx+1;
        int endLength = word.length()-letterIdx;

        // Determine word orientation and check for fit
        if((crossword[row -1][col]=='\u0000' && crossword[row +1][col]=='\u0000' )){
            // Vertical orientation
            for(int n = letterIdx; n >0; n--){ // first part of word
                if(crossword[row -n][col] != '\u0000' || crossword[row-n][col-1] != '\u0000' || crossword[row-n][col+1] != '\u0000'){
                    fits = false;
                }
            }
            for(int n = 1; n<lettersToPlace.length-letterIdx; n++){ // second part of word
                if (crossword[row +n][col] != '\u0000' || crossword[row+n][col-1] != '\u0000' || crossword[row+n][col+1] != '\u0000'){
                    fits = false;
                }
            }
            // Ensures words don't run into each other consecutively
            if(crossword[row-beginLength][col] != '\u0000' || crossword[row+beginLength][col] != '\u0000'){
                fits = false;
            }
        }
        if((crossword[row][col -1]=='\u0000' && crossword[row][col +1]=='\u0000' )){
            // Horizontal orientation
            for(int n = letterIdx; n >0; n--){ // first part of word
                if (crossword[row][col -n] != '\u0000' || crossword[row-1][col-n] != '\u0000' || crossword[row+1][col-n] != '\u0000'){
                    fits = false;
                }
            }
            for(int n = 1; n<lettersToPlace.length-letterIdx; n++){ // second part of word
                if (crossword[row][col +n] != '\u0000' || crossword[row-1][col+n] != '\u0000' || crossword[row+1][col+n] != '\u0000'){
                    fits = false;
                }
            }
            // Ensures words don't run into each other consecutively
            if(crossword[row][col-beginLength] != '\u0000' || crossword[row][col+endLength] != '\u0000'){
                fits = false;
            }
        }
        return fits;
    }

    /**
     * Get the list of words placed horizontally
     * @return an ArrayList<String> of the words placed horizontally
     */
    public ArrayList<String> getHorizontalWords() {
        return horizontalWords;
    }

    /**
     * Get the list of words placed vertically
     * @return an ArrayList<String> of the words placed vertically
     */
    public ArrayList<String> getVerticalWords() {
        return verticalWords;
    }
}
