package org.ChrisMeiersMollyNhi;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class WordDatabaseGeneratorTest {

    /** Generator object used in test **/
    WordDatabaseGenerator generator;

    @BeforeEach
    void setUp() {
        this.generator = new WordDatabaseGenerator();
    }

    @Test
    void returnRandomWord() {
        for (int i = 0; i < 10; i++) {
            System.out.println(generator.returnRandomWord());
        }
    }
}