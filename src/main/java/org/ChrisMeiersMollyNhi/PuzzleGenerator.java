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

public class PuzzleGenerator {

    /** ArrayList of words from which to create the puzzle */
    private ArrayList<String> words;

    /** The number of rows available to place letters within */
    private int numRows;

    /** The number of columns available to place letters within */
    private int numCols;

    /**
     * Initialize list of words and size of puzzle grid
     *
     * @param words the list of words from which to create the puzzle
     */
    public PuzzleGenerator(ArrayList<String> words){
        this.words = words;
        this.numRows = 100; // Temporary value for initial development
        this.numCols = 100; // Temporary value for initial development
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
        String firstWord = words.getFirst();
        char[] chars = firstWord.toCharArray();
        for(char c:chars){
            int row = numRows/2;
            for(int col=(numCols/2)-(chars.length/2); col<(numCols/2)-(chars.length/2)+chars.length; col++){
                crossword[row][col] = c;
            }
        }

        // Place the rest of the words on the puzzle
        for(int i=1; i< words.size(); i++){
            String word = words.get(i);
            char[] letters = word.toCharArray();

            for(int row = 0; row < numRows; row++){
                for(int col = 0; col < numCols; col++){
                    for(char letter : letters){
                        // Check if letter in puzzle space matches a letter in the word to place
                        // and confirm that the space is available as an intersection point
                        if(crossword[row][col] == letter && validateOpen(crossword,row,col)){
                            // Pick direction
                            
                            // Fill in word in appropriate direction
                        }
                    }
                }
            }

        }

        return crossword;
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
        if((crossword[row-1][col]!='\u0000' || crossword[row][col-1]!='\u0000' )&&
                (crossword[row+1][col]!='\u0000' || crossword[row][col+1]!='\u0000')
        ) {
            open = false;
        }
        return open;
    }
}
