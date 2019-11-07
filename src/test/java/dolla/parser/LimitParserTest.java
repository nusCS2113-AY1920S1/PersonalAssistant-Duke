package dolla.parser;

import dolla.command.Command;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@@author Weng-Kexin
public class LimitParserTest {

    private static final String expectedCommandInfo = "limit";
    private static final String expectedValidResult = "AddLimitCommand";
    private static final String expectedInvalidResult = "ErrorCommand";

    @Test
    public void parseValidListInputTest() {
        String validListInput = "limits";
        LimitParser limitParser = new LimitParser(validListInput);
        Command command = limitParser.parseInput();
        assertEquals(expectedCommandInfo, command.getCommandInfo());
    }

    @Test
    public void parseInvalidListInputTest() {
        String invalidListInput = "limit";
        LimitParser limitParser = new LimitParser(invalidListInput);
        Command command = limitParser.parseInput();
        assertEquals(expectedInvalidResult, command.getCommandInfo());
    }

    @Test
    public void parseValidSetFormatTest() {
        String validSetFormatLine = "set budget 10 daily";
        LimitParser limitParser = new LimitParser(validSetFormatLine);
        Command command = limitParser.parseInput();
        assertEquals(expectedValidResult, command.getCommandInfo());
    }

    @Test
    public void parseInvalidSetFormatTest() {
        String invalidSetFormatLine = "set budget 5";
        LimitParser limitParser = new LimitParser(invalidSetFormatLine);
        Command command = limitParser.parseInput();
        assertEquals(expectedInvalidResult, command.getCommandInfo());
    }

    @Test
    public void parseValidRemoveTest() {
        String validSetFormatLine = "remove 1";
        LimitParser limitParser = new LimitParser(validSetFormatLine);
        Command command = limitParser.parseInput();
        assertEquals(expectedCommandInfo, command.getCommandInfo());
    }

    @Test
    public void parseInvalidRemoveTest() {
        String validSetFormatLine = "remove five";
        LimitParser limitParser = new LimitParser(validSetFormatLine);
        Command command = limitParser.parseInput();
        assertEquals(expectedInvalidResult, command.getCommandInfo());
    }
}
