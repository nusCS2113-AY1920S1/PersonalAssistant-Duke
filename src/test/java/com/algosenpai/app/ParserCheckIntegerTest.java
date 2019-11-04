package com.algosenpai.app;

import com.algosenpai.app.logic.parser.Parser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ParserCheckIntegerTest {

    @Test
    void testParserIsIntegerEmptyString() {
        boolean isInteger = Parser.isInteger("");
        Assertions.assertFalse(isInteger);
    }

    @Test
    void testParserIsIntegerStringInput() {
        boolean isInteger = Parser.isInteger("testing");
        Assertions.assertFalse(isInteger);
    }

    @Test
    void testParserIsIntegerDoubleInput() {
        boolean isInteger = Parser.isInteger("1.0");
        Assertions.assertFalse(isInteger);
    }

    @Test
    void testParserIsIntegerIntegerInput() {
        boolean isInteger = Parser.isInteger("1");
        Assertions.assertTrue(isInteger);
    }
}
