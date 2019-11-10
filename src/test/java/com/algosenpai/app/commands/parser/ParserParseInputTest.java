package com.algosenpai.app.commands.parser;

import com.algosenpai.app.logic.parser.Parser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import java.util.ArrayList;

class ParserParseInputTest extends ApplicationTest {

    @Test
    void testParserTrim() {
        ArrayList<String> actualText = Parser.parseInput(" menu ");
        ArrayList<String> expectedText = new ArrayList<>();
        expectedText.add("menu");
        Assertions.assertEquals(expectedText, actualText);
    }

    @Test
    void testParserMultipleSpaces() {
        ArrayList<String> actualText = Parser.parseInput("menu         select");
        ArrayList<String> expectedText = new ArrayList<>();
        expectedText.add("menu");
        expectedText.add("select");
        Assertions.assertEquals(expectedText, actualText);
    }

    @Test
    void testParserLowerInput() {
        ArrayList<String> actualText = Parser.parseInput("menu select");
        ArrayList<String> expectedText = new ArrayList<>();
        expectedText.add("menu");
        expectedText.add("select");
        Assertions.assertEquals(expectedText, actualText);
    }

    @Test
    void testParserOneWordInputLowerCase() {
        ArrayList<String> actualText = Parser.parseInput("MENU");
        ArrayList<String> expectedText = new ArrayList<>();
        expectedText.add("menu");
        Assertions.assertEquals(expectedText, actualText);
    }

    @Test
    void testParserMultipleWordsInputLowerCase() {
        ArrayList<String> actualText = Parser.parseInput("MENU SELECT");
        ArrayList<String> expectedText = new ArrayList<>();
        expectedText.add("menu");
        expectedText.add("SELECT");
        Assertions.assertEquals(expectedText, actualText);
    }

    @Test
    void testParserLowerCaseOnlyOnFirstWord() {
        ArrayList<String> actualText = Parser.parseInput("menu SELECT");
        ArrayList<String> expectedText = new ArrayList<>();
        expectedText.add("menu");
        expectedText.add("SELECT");
        Assertions.assertEquals(expectedText, actualText);
    }
}
