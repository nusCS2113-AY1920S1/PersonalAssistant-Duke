package parser;

import command.AddCommand;
import command.Command;
import command.DeleteCommand;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static parser.Parser.parseAdd;
import static parser.Parser.parseDelete;

class ParserTest {

    @Test
    void parseAddTest() {
        try {
            String[] taskInfo = {"add", "w/happy w/clap along friends"};
            Command returnedObject = parseAdd(taskInfo);
            assertNotNull(returnedObject);
            assertTrue(returnedObject instanceof AddCommand);
        } catch(Exception e) {
            fail("parseAdd method failed with the error message: " + e.getMessage());
        }
    }

    @Test
    void parseDeleteTest() {
        try {
            String[] taskInfo = {"delete", "w/happy"};
            Command returnedObject = parseDelete(taskInfo);
            assertNotNull(returnedObject);
            assertTrue(returnedObject instanceof DeleteCommand);
        } catch(Exception e) {
            fail("parseDelete method failed with the error message: " + e.getMessage());
        }
    }

    @Test
    void parseSearchTest() {
    }

    @Test
    void parseListTest() {
    }

    @Test
    void parseHistoryTest() {
    }

    @Test
    void parseSearchFrequencyTest() {
    }

    @Test
    void parseEditTest() {
    }

    @Test
    void parseTagTest() {
    }

    @Test
    void parseQuizTest() {
    }
}