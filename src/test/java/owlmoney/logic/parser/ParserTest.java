package owlmoney.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import owlmoney.logic.parser.exception.ParserException;

public class ParserTest {
    @Test
    void parseFirstField_normalInput_success() {
        Parser testParseCommand = new ParseCommand();
        String testInput =
                "/add /bonds /name test bond /amount 500 /rate 2.01 /year 10 /date 1/2/2019 /from test bank";
        String actualOutput = testParseCommand.parseFirstField(testInput);
        String expectedOutput = "/add";
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void removeFirstField_normalInput_success() throws ParserException {
        Parser testParseCommand = new ParseCommand();
        String testCommand = "/add";
        String testInput =
                "/add /bonds /name test bond /amount 500 /rate 2.01 /year 10 /date 1/2/2019 /from test bank";
        String actualOutput = testParseCommand.removeFirstField(testInput,testCommand);
        String expectedOutput =
                "/bonds /name test bond /amount 500 /rate 2.01 /year 10 /date 1/2/2019 /from test bank";
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void removeFirstField_reservedKeywordExitInput_success() throws ParserException {
        Parser testParseCommand = new ParseCommand();
        String testCommand = "/exit";
        String testInput =
                "/exit";
        String actualOutput = testParseCommand.removeFirstField(testInput,testCommand);
        String expectedOutput =
                "";
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void removeFirstField_reservedKeywordUpdateInput_success() throws ParserException {
        Parser testParseCommand = new ParseCommand();
        String testCommand = "/update";
        String testInput =
                "/update";
        String actualOutput = testParseCommand.removeFirstField(testInput,testCommand);
        String expectedOutput =
                "";
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void removeFirstField_reservedKeywordHelpInput_success() throws ParserException {
        Parser testParseCommand = new ParseCommand();
        String testCommand = "/help";
        String testInput =
                "/help";
        String actualOutput = testParseCommand.removeFirstField(testInput,testCommand);
        String expectedOutput =
                "";
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void removeFirstField_incompleteCommand_throwsParserException() throws ParserException {
        Parser testParseCommand = new ParseCommand();
        String testCommand = "/help /t";
        String testInput =
                "/help";
        ParserException thrown = assertThrows(ParserException.class, () ->
                testParseCommand.removeFirstField(testInput,testCommand),
                "Expected ParserException to throw, but it didnt");
        String expectedOutput = "Incomplete command provided";
        String actualOutput = thrown.getMessage();
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void removeListFirstField_incompleteCommand_throwsParserException() throws ParserException {
        Parser testParseCommand = new ParseCommand();
        String testCommand = "/list test";
        String testInput =
                "/list";
        ParserException thrown = assertThrows(ParserException.class, () ->
                        testParseCommand.removeListFirstField(testInput,testCommand),
                "Expected ParserException to throw, but it didnt");
        String expectedOutput = "Incomplete command provided";
        String actualOutput = thrown.getMessage();
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void removeListFirstField_reservedKeywordSavingsInput_success() throws ParserException {
        Parser testParseCommand = new ParseCommand();
        String testCommand = "/list";
        String testInput =
                "/list /savings";
        String actualOutput = testParseCommand.removeListFirstField(testInput,testCommand);
        String expectedOutput =
                "/savings";
        assertEquals(expectedOutput, actualOutput);
    }
}
