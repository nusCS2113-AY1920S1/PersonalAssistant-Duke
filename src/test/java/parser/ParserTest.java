package parser;

import command.Command;
import command.AddTagCommand;
import command.EditCommand;
import command.SearchFrequencyCommand;
import command.HistoryCommand;
import command.SearchCommand;
import command.DeleteCommand;
import command.AddCommand;
import command.ListCommand;
import command.QuizCommand;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static parser.Parser.parseAdd;
import static parser.Parser.parseDelete;
import static parser.Parser.parseList;
import static parser.Parser.parseTag;
import static parser.Parser.parseSearch;
import static parser.Parser.parseQuiz;
import static parser.Parser.parseSearchFrequency;
import static parser.Parser.parseHistory;
import static parser.Parser.parseEdit;

class ParserTest {

    @Test
    void parseAddTest() {
        try {
            String[] taskInfo = {"add", "w/happy m/clap along friends"};
            Command returnedObject = parseAdd(taskInfo);
            assertNotNull(returnedObject);
            assertTrue(returnedObject instanceof AddCommand);
        } catch (Exception e) {
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
        } catch (Exception e) {
            fail("parseDelete method failed with the error message: " + e.getMessage());
        }
    }

    @Test
    void parseSearchTest() {
        try {
            String[] taskInfo = {"search", "w/happy"};
            Command returnedObject = parseSearch(taskInfo);
            assertNotNull(returnedObject);
            assertTrue(returnedObject instanceof SearchCommand);
        } catch (Exception e) {
            fail("parseSearch method failed with the error message: " + e.getMessage());
        }
    }

    @Test
    void parseListTest() {
        try {
            String[] taskInfo = {"list"};
            Command returnedObject = parseList(taskInfo);
            assertNotNull(returnedObject);
            assertTrue(returnedObject instanceof ListCommand);
        } catch (Exception e) {
            fail("parseList method failed with the error message: " + e.getMessage());
        }
    }

    @Test
    void parseHistoryTest() {
        try {
            String[] taskInfo = {"history", "5"};
            Command returnedObject = parseHistory(taskInfo);
            assertNotNull(returnedObject);
            assertTrue(returnedObject instanceof HistoryCommand);
        } catch (Exception e) {
            fail("parseHistory method failed with the error message: " + e.getMessage());
        }
    }

    @Test
    void parseSearchFrequencyTest() {
        try {
            String[] taskInfo = {"freq", "o/asc"};
            Command returnedObject = parseSearchFrequency(taskInfo);
            assertNotNull(returnedObject);
            assertTrue(returnedObject instanceof SearchFrequencyCommand);

            taskInfo[1] = "o/desc";
            returnedObject = parseSearchFrequency(taskInfo);
            assertNotNull(returnedObject);
            assertTrue(returnedObject instanceof SearchFrequencyCommand);
        } catch (Exception e) {
            fail("parseSearchFrequency method failed with the error message: " + e.getMessage());
        }
    }

    @Test
    void parseEditTest() {
        try {
            String[] taskInfo = {"edit", "w/happy m/new"};
            Command returnedObject = parseEdit(taskInfo);
            assertNotNull(returnedObject);
            assertTrue(returnedObject instanceof EditCommand);
        } catch (Exception e) {
            fail("parseEdit method failed with the error message: " + e.getMessage());
        }
    }

    @Test
    void parseTagTest() {
        try {
            String[] taskInfo = {"tag", "w/happy t/emotion"};
            Command returnedObject = parseTag(taskInfo);
            assertNotNull(returnedObject);
            assertTrue(returnedObject instanceof AddTagCommand);
        } catch (Exception e) {
            fail("parseTag method failed with the error message: " + e.getMessage());
        }
    }

    @Test
    void parseQuizTest() {
        try {
            String[] taskInfo = {"quiz"};
            Command returnedObject = parseQuiz(taskInfo);
            assertNotNull(returnedObject);
            assertTrue(returnedObject instanceof QuizCommand);
        } catch (Exception e) {
            fail("parseQuiz method failed with the error message: " + e.getMessage());
        }
    }
}