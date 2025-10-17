package org.ChrisMeiersMollyNhi;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PuzzleGeneratorTest {

    private PuzzleGenerator gen1;
    private PuzzleGenerator gen2;

    @BeforeEach
    void setUp() {
        // good list
        ArrayList<String> wordlist1 = new ArrayList<String>();
        wordlist1.add("cat");
        wordlist1.add("tree");
        wordlist1.add("gleam");
        wordlist1.add("magic");

        // bad list
        ArrayList<String> wordlist2 = new ArrayList<String>();
        wordlist2.add("cat");
        wordlist2.add("dog");
        wordlist2.add("tree");
        wordlist2.add("beam");

        this.gen1 = new PuzzleGenerator(wordlist1);
        this.gen2 = new PuzzleGenerator(wordlist2);
    }

    @AfterEach
    void tearDown() {
    }

    /**
     * Test that word list is validated correctly
     */
    @Test
    void isValidWordList() {
        assertTrue(this.gen1.isValidWordList());
        assertFalse(this.gen2.isValidWordList());
    }

//     /**
//      * Test that puzzle is generated correctly and contains all words from word list
//      */
//     @Test
//     void generate() {
//         char[][] generated = this.gen1.generate();
// //        System.out.println(generated);
//         int letterCount = 0;
//         for (int i = 0; i < generated.length; i++) {
//             for (int j = 0; j < generated[i].length; j++) {
//                 System.out.print(generated[i][j]);
//                 if (generated[i][j] != '\u0000') {
// //                    System.out.println(generated[i][j]);
//                     letterCount++;
//                 }
//             }
//             System.out.println();
//         }

//         assertEquals(14, letterCount);
//     }

    /**
     * Test that available intersection spaces are recognized correctly
     */
    @Test
    void validateOpen() {
        char[][] testCW = new char[3][3];

        assertTrue(this.gen1.validateOpen(testCW,1,1));

        char[][] testCW2 = new char[3][3];
        testCW2[0][1]='x';
        testCW2[1][0]='x';
        testCW2[1][2]='x';
        testCW2[2][1]='x';

        assertFalse(this.gen1.validateOpen(testCW2,1,1));
    }
}