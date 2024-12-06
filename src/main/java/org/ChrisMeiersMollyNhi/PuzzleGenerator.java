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

    /** An ArrayList of the words placed vertically */
    private ArrayList<String> verticalWords;

    /** An ArrayList of the words placed horizontally */
    private ArrayList<String> horizontalWords;

    private ArrayList<ArrayList<Integer>> horizIndices;
    private ArrayList<ArrayList<Integer>> vertIndices;

    /** An int[][] of the ints which represent the word numbers */
    private int[][] shadowGrid;

    /**
     * Initialize list of words and size of puzzle grid
     *
     * @param words the list of words from which to create the puzzle
     */
    public PuzzleGenerator(ArrayList<String> words){
        this.words = words;
        this.numRows = 75; // Temporary value for initial development
        this.numCols = 75; // Temporary value for initial development
        this.verticalWords = new ArrayList<>(); // Initialize verticalWords
        this.horizontalWords = new ArrayList<>(); // Initialize horizontalWords
        this.horizIndices = new ArrayList<ArrayList<Integer>>();
        this.vertIndices = new ArrayList<ArrayList<Integer>>();
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
    public int[][] generate(){
        char[][] crossword = new char[numRows][numCols];
        this.shadowGrid = new int[numRows][numCols];
        //init all spaces in shadowGrid to -1
        for(int r=0;r<numRows;r++) {
            for(int c=0;c<numCols;c++) {
                shadowGrid[r][c]=-1;
            }
        }

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
                            placeOtherWords(letter, word, crossword, row, col, i);
                            placed = true;
                        }
                    }
                }
            }
        }

        trim();
        return this.shadowGrid;
    }

    /**
     * scans for rows and columns of this.shadowGrid that contain ONLY -1, and removes those rows or columns
     */
    private void trim () {
        int numRows = this.shadowGrid.length;
        int numCols = this.shadowGrid[0].length;
        int startColsToTrim = 0;
        int startRowsToTrim = 0;
        int endColsToTrim = 0;
        int endRowsToTrim = 0;
        boolean postBlock=false; //activates when we hit the first letter
        for (int row=0;row<numRows;row++) {

            boolean emptyRow=true;
            for (int col=0;col<numCols;col++){
                if(this.shadowGrid[row][col]!=-1){
                    emptyRow=false;
                    postBlock=true;
                    break;
                }
            }
            if (emptyRow){
                if(postBlock){
                    endRowsToTrim++;
                } else {
                    startRowsToTrim++;
                }
            }
        }

        postBlock=false;
        for (int col=0;col<numCols;col++) {
            boolean emptyCol=true;
            for (int row=0;row<numRows;row++){
                if(this.shadowGrid[row][col]!=-1){
                    emptyCol=false;
                    postBlock=true;
                    break;
                }
            }
            if(emptyCol){
                if(postBlock){
                    endColsToTrim++;
                } else {
                    startColsToTrim++;
                }
            }
        }

        //trim off excess rows and cols
        int newNumRows = numRows - startRowsToTrim - endRowsToTrim;
        int newNumCols = numCols - startColsToTrim - endColsToTrim;

        // Determine the size of the square grid
        int squareSize = Math.max(newNumRows, newNumCols);
        int[][] trimmedGrid = new int[squareSize][squareSize];

        // Fill the square grid with a default value (-1)
        for (int i = 0; i < squareSize; i++) {
            for (int j = 0; j < squareSize; j++) {
                trimmedGrid[i][j] = -1;
            }
        }

        // Calculate offsets to center the data
        int rowOffset = (squareSize - newNumRows) / 2;
        int colOffset = (squareSize - newNumCols) / 2;

        // Copy the relevant data into the square grid, centered
        for (int row = 0; row < newNumRows; row++) {
            for (int col = 0; col < newNumCols; col++) {
                trimmedGrid[row + rowOffset][col + colOffset] =
                        this.shadowGrid[row + startRowsToTrim][col + startColsToTrim];
            }
        }

        // Copy the relevant data
        for (int row = 0; row < newNumRows; row++) {
            System.arraycopy(
                    this.shadowGrid[row + startRowsToTrim],
                    startColsToTrim,
                    trimmedGrid[row],
                    0,
                    newNumCols
            );
        }

        // Assign the trimmed grid back to shadowGrid
        this.shadowGrid = trimmedGrid;
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
    private void placeOtherWords(char letter, String word, char[][] crossword, int row, int col, int wordNumber) {
        wordNumber++;
        char[] lettersToPlace = word.toCharArray();
        int idx = -1;
        for(int i=0; i<lettersToPlace.length; i++){
            if(lettersToPlace[i] == letter){
                idx = i;
            }
        }
        boolean firstLetter = true;

        // Pick direction
        // and fill in word in appropriate direction
        if(
                (isValidIndex(row-1, col) && crossword[row -1][col]=='\u0000' ) ||
                (isValidIndex(row+1, col) && crossword[row +1][col]=='\u0000')
        ){
            // Place word vertical
            for(int n = idx; n >0; n--){ // first part of word
                if (firstLetter) {
                    this.shadowGrid[row-n][col]=wordNumber;
                    firstLetter=false;
                }else{
                    this.shadowGrid[row-n][col]=0;
                }
                crossword[row -n][col] = lettersToPlace[idx-n];

                if(vertIndices.size()!=this.verticalWords.size()){
                    ArrayList<Integer> index = new ArrayList<>();
                    index.add(row);
                    index.add(col);
                    vertIndices.add(index);
                }
            }
            for(int n = 1; n<lettersToPlace.length-idx; n++){ // second part of word
                crossword[row +n][col] = lettersToPlace[idx+n];
                this.shadowGrid[row+n][col]=0;
            }

            // Add word to list of words
            this.verticalWords.add(wordNumber+" "+word);
        }
        if(
                isValidIndex(row, col-1) && isValidIndex(row, col+1) &&
                crossword[row][col -1]=='\u0000' && crossword[row][col +1]=='\u0000'
        ){
            // Place word horizontal
            for(int n = idx; n >0; n--){ // first part of word
                if (firstLetter) {
                    this.shadowGrid[row][col-n]=wordNumber;
                    firstLetter=false;
                }else{
                    this.shadowGrid[row][col-n]=0;
                }
                crossword[row][col -n] = lettersToPlace[idx-n];

                if(horizIndices.size()!=this.horizontalWords.size()){
                    ArrayList<Integer> index = new ArrayList<>();
                    index.add(row);
                    index.add(col);
                    horizIndices.add(index);
                }
            }
            for(int n = 1; n<lettersToPlace.length-idx; n++){ // second part of word
                crossword[row][col +n] = lettersToPlace[idx+n];
                this.shadowGrid[row][col+n]=0;
            }

            // Add word to list of words
            this.horizontalWords.add(wordNumber+" "+word);
        }
    }

    /**
     * Place the first word in the center of the puzzle
     *
     * @param crossword the crossword puzzle grid
     */
    private void placeFirstWord(char[][] crossword) {
        String firstWord = words.getFirst();
        this.horizontalWords.add("1 "+firstWord);
        char[] chars = firstWord.toCharArray();
        int row = numRows/2;
        int charIdx = 0;
        boolean firstLetter = true;
        for(int col=(numCols/2)-(chars.length/2); col<(numCols/2)-(chars.length/2)+chars.length; col++){
            char c = chars[charIdx];
            crossword[row][col] = c;
            charIdx++;
            if (firstLetter){
                shadowGrid[row][col]=1;
            } else{
                shadowGrid[row][col]=0;
            }

            // Save index of first letter
            if(horizIndices.size()==0){
                ArrayList<Integer> idx = new ArrayList<>();
                idx.add(row);
                idx.add(col);
                horizIndices.add(idx);
            }
            firstLetter=false;
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
        if(     (
                    isValidIndex(row-1, col) && isValidIndex(row, col-1) &&
                    crossword[row-1][col]!='\u0000' && crossword[row][col-1]!='\u0000'
                )|| (
                    isValidIndex(row+1, col) && isValidIndex(row, col+1) &&
                    crossword[row+1][col]!='\u0000' && crossword[row][col+1]!='\u0000'
                )||(
                    isValidIndex(row-1, col) && isValidIndex(row, col+1) &&
                    crossword[row-1][col]!='\u0000' && crossword[row][col+1]!='\u0000'
                )||(
                    isValidIndex(row+1, col) && isValidIndex(row, col-1) &&
                    crossword[row+1][col]!='\u0000' && crossword[row][col-1]!='\u0000'
                )
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
        if(
                isValidIndex(row-1, col) && isValidIndex(row+1, col) &&
                crossword[row -1][col]=='\u0000' && crossword[row +1][col]=='\u0000'
        ){
            // Vertical orientation
            for(int n = letterIdx; n >0; n--){ // first part of word
                if(
                        (isValidIndex(row-n, col) && crossword[row -n][col] != '\u0000') ||
                        (isValidIndex(row-n, col-1) && crossword[row-n][col-1] != '\u0000') ||
                        (isValidIndex(row-n, col+1) && crossword[row-n][col+1] != '\u0000')
                ){
                    fits = false;
                }
            }
            for(int n = 1; n<lettersToPlace.length-letterIdx; n++){ // second part of word
                if (
                        (isValidIndex(row+n, col) && crossword[row +n][col] != '\u0000') ||
                        (isValidIndex(row+n, col-1) && crossword[row+n][col-1] != '\u0000') ||
                        (isValidIndex(row+n, col+1) && crossword[row+n][col+1] != '\u0000')
                ){
                    fits = false;
                }
            }
            // Ensures words don't run into each other consecutively
            if(
                    (isValidIndex(row-beginLength, col) && crossword[row-beginLength][col] != '\u0000') ||
                    (isValidIndex(row+beginLength, col) && crossword[row+beginLength][col] != '\u0000')
            ){
                fits = false;
            }
        }
        if((crossword[row][col -1]=='\u0000' && crossword[row][col +1]=='\u0000' )){
            // Horizontal orientation
            for(int n = letterIdx; n >0; n--){ // first part of word
                if (
                        (isValidIndex(row, col-n) && crossword[row][col -n] != '\u0000') ||
                        (isValidIndex(row-1, col-n) && crossword[row-1][col-n] != '\u0000') ||
                        (isValidIndex(row+1, col-n) && crossword[row+1][col-n] != '\u0000')
                ){
                    fits = false;
                }
            }
            for(int n = 1; n<lettersToPlace.length-letterIdx; n++){ // second part of word
                if (
                        (isValidIndex(row, col+n) && crossword[row][col +n] != '\u0000') ||
                        (isValidIndex(row-1, col+n) && crossword[row-1][col+n] != '\u0000') ||
                        (isValidIndex(row+1, col+n) && crossword[row+1][col+n] != '\u0000')
                ){
                    fits = false;
                }
            }
            // Ensures words don't run into each other consecutively
            if(
                    (isValidIndex(row, col-beginLength) && crossword[row][col-beginLength] != '\u0000' )||
                    (isValidIndex(row, col+endLength) && crossword[row][col+endLength] != '\u0000')
            ){
                fits = false;
            }
        }
        return fits;
    }

    /**
     * Checks if the given coordinates are within bounds of the puzzle grid
     * @param row the row coordinate to check
     * @param col the column coordinate to check
     * @return a boolean value indicating if the coordinates are valid
     */
    private boolean isValidIndex(int row, int col) {
        return (row >= 0 && row < numRows && col >= 0 && col < numCols);
    }


    /**
     * Gets the set of indices to be labeled with an index for each work which will be used for hint matching
     * @param crossword the crossword puzzle
     * @return an ArrayList<ArrayList<Integer>> containing the indices of the box to be labeled
     */
    public ArrayList<ArrayList<Integer>> getHorizontalWordIndices(char[][] crossword){
        ArrayList<ArrayList<Integer>> indices = new ArrayList<ArrayList<Integer>>();
        ArrayList<String> newHorizWords = new ArrayList<String>();

        for(int row=0; row<crossword.length; row++){
            for(int col=0; col<crossword[row].length; col++){
                if(crossword[row][col] != '\u0000' && crossword[row][col-1] == '\u0000' &&
                crossword[row-1][col] == '\u0000' && crossword[row+1][col] == '\u0000'){
                    ArrayList<Integer> idx = new ArrayList<>();
                    idx.add(row);
                    idx.add(col);
                    indices.add(idx);

                    int listIndex = horizIndices.indexOf(idx);
                    String word = horizontalWords.get(listIndex);
                    newHorizWords.add(word);
                }
            }
        }
        this.horizontalWords = newHorizWords;
        return indices;
    }

    /**
     * Gets the set of indices to be labeled with an index for each work which will be used for hint matching
     * @param crossword the crossword puzzle
     * @return an ArrayList<ArrayList<Integer>> containing the indices of the box to be labeled
     */
    public ArrayList<ArrayList<Integer>> getVerticalWordIndices(char[][] crossword){
        ArrayList<ArrayList<Integer>> indices = new ArrayList<ArrayList<Integer>>();
        ArrayList<String> newVertWords = new ArrayList<String>();

        for(int row=0; row<crossword.length; row++){
            for(int col=0; col<crossword[row].length; col++){
                if(crossword[row][col] != '\u0000' && crossword[row-1][col] == '\u0000' &&
                        crossword[row][col-1] == '\u0000' && crossword[row][col+1] == '\u0000'){
                    ArrayList<Integer> idx = new ArrayList<>();
                    idx.add(row);
                    idx.add(col);
                    indices.add(idx);

                    int listIndex = vertIndices.indexOf(idx);
                    String word = verticalWords.get(listIndex);
                    newVertWords.add(word);
                }
            }
        }
        this.verticalWords = newVertWords;
        return indices;
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
