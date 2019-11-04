package parser;

import command.Command;
import command.AddTagCommand;
import command.EditCommand;
import command.ExitCommand;
import command.HelpCommand;
import command.SearchBeginCommand;
import command.SearchFrequencyCommand;
import command.HistoryCommand;
import command.SearchCommand;
import command.DeleteCommand;
import command.AddCommand;
import command.ListCommand;
import command.QuizCommand;
import command.SetReminderCommand;
import exception.WrongAddFormatException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static parser.Parser.parseAdd;
import static parser.Parser.parseDelete;
import static parser.Parser.parseHelp;
import static parser.Parser.parseList;
import static parser.Parser.parseReminder;
import static parser.Parser.parseSearchBegin;
import static parser.Parser.parseTag;
import static parser.Parser.parseSearch;
import static parser.Parser.parseQuiz;
import static parser.Parser.parseSearchFrequency;
import static parser.Parser.parseHistory;
import static parser.Parser.parseEdit;

class ParserTest {

    private static final String addInput = "add w/happy m/clap along friends";
    private static final String deleteInput = "delete w/happy";
    private static final String searchInput = "search w/happy";
    private static final String listInput = "list";
    private static final String historyInput = "history 5";
    private static final String freqInput = "freq o/asc";
    private static final String editInput = "edit w/happy m/new";
    private static final String tagInput = "tag w/happy t/emotion";
    private static final String quizInput = "quiz";
    private static final String reminderInput = "schedule";
    private static final String searchBeginInput = "search_begin w/a";
    private static final String exitInput = "exit";
    private static final String helpInput = "help";


    @Test
    void parseTest() {
        String input = addInput;
        Command returnedObject = Parser.parse(input);
        assertNotNull(returnedObject);
        assertTrue(returnedObject instanceof AddCommand);

        input = deleteInput;
        returnedObject = Parser.parse(input);
        assertNotNull(returnedObject);
        assertTrue(returnedObject instanceof DeleteCommand);

        input = searchInput;
        returnedObject = Parser.parse(input);
        assertNotNull(returnedObject);
        assertTrue(returnedObject instanceof SearchCommand);

        input = listInput;
        returnedObject = Parser.parse(input);
        assertNotNull(returnedObject);
        assertTrue(returnedObject instanceof ListCommand);

        input = historyInput;
        returnedObject = Parser.parse(input);
        assertNotNull(returnedObject);
        assertTrue(returnedObject instanceof HistoryCommand);

        input = freqInput;
        returnedObject = Parser.parse(input);
        assertNotNull(returnedObject);
        assertTrue(returnedObject instanceof SearchFrequencyCommand);

        input = editInput;
        returnedObject = Parser.parse(input);
        assertNotNull(returnedObject);
        assertTrue(returnedObject instanceof EditCommand);

        input = tagInput;
        returnedObject = Parser.parse(input);
        assertNotNull(returnedObject);
        assertTrue(returnedObject instanceof AddTagCommand);

        input = quizInput;
        returnedObject = Parser.parse(input);
        assertNotNull(returnedObject);
        assertTrue(returnedObject instanceof QuizCommand);

        input = reminderInput;
        returnedObject = Parser.parse(input);
        assertNotNull(returnedObject);
        assertTrue(returnedObject instanceof SetReminderCommand);

        input = searchBeginInput;
        returnedObject = Parser.parse(input);
        assertNotNull(returnedObject);
        assertTrue(returnedObject instanceof SearchBeginCommand);

        input = exitInput;
        returnedObject = Parser.parse(input);
        assertNotNull(returnedObject);
        assertTrue(returnedObject instanceof ExitCommand);

        input = helpInput;
        returnedObject = Parser.parse(input);
        assertNotNull(returnedObject);
        assertTrue(returnedObject instanceof HelpCommand);
    }

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
    void parseReminderTest() {
        try {
            String[] taskInfo = {"schedule"};
            Command returnedObject = parseReminder(taskInfo);
            assertNotNull(returnedObject);
            assertTrue(returnedObject instanceof SetReminderCommand);
        } catch (Exception e) {
            fail("parseReminder method failed with the error message: " + e.getMessage());
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

    @Test
    void parseHelpTest() {
        try {
            String[] taskInfo = {"help"};
            Command returnedObject = parseHelp(taskInfo);
            assertNotNull(returnedObject);
            assertTrue(returnedObject instanceof HelpCommand);
        } catch (Exception e) {
            fail("parseHelp method failed with the error message: " + e.getMessage());
        }
    }

    @Test
    void parseSearchBeginTest() {
        try {
            String[] taskInfo = {"search_begin", "w/a"};
            Command returnedObject = parseSearchBegin(taskInfo);
            assertNotNull(returnedObject);
            assertTrue(returnedObject instanceof SearchBeginCommand);
        } catch (Exception e) {
            fail("parseSearchBegin method failed with the error message: " + e.getMessage());
        }
    }
}