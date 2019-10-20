package compal.logic.parser;

import compal.logic.command.CommandResult;
import compal.logic.command.exceptions.CommandException;
import compal.logic.parser.exceptions.ParserException;
import compal.model.tasks.TaskList;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CommandParserTestUtil {

    /**
     * Asserts that the parsing of user input by  parserManager is successful and the command created
     * equals to expectedCommand .
     */
    static void assertParseSuccess(CommandParser commandParser, String userInput,
                                   CommandResult expectedCommand, TaskList taskList) {
        try {
            CommandResult command = commandParser.parseCommand(userInput).commandExecute(taskList);
            assertEquals(expectedCommand.feedbackToUser, command.feedbackToUser);
        } catch (ParserException | CommandException | ParseException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    /**
     * Asserts that the parsing of user input by parser is unsuccessful and the error message
     * equals to the errorMessage.
     */
    static void assertParseFailure(CommandParser parserManager, String userInput, String expectedMessage) {
        try {
            parserManager.parseCommand(userInput);
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParserException | ParseException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }
}