package dolla.parser;

import dolla.command.Command;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@@author Weng-Kexin
public class LimitParserTest {

    private static final String expectedInvalidResult = "ErrorCommand";

    @Test
    public void parseValidListInputTest() {
        LimitParser limitParser;
        Command command;

        String inputLine1 = "limits";
        String expected1 = "limit";
        limitParser = new LimitParser(inputLine1);
        command = limitParser.parseInput();
        assertEquals(expected1, command.getCommandInfo());

        String inputLine2 = "set budget 10 daily";
        String expected2 = "budget 10.0 daily";
        limitParser = new LimitParser(inputLine2);
        command = limitParser.parseInput();
        assertEquals(expected2, command.getCommandInfo());

        String inputLine3 = "remove 1";
        String expected3 = "limit";
        limitParser = new LimitParser(inputLine3);
        command = limitParser.parseInput();
        assertEquals(expected3, command.getCommandInfo());

        String inputLine4 = "remaining daily budget";
        String expected4 = "daily budget";
        limitParser = new LimitParser(inputLine4);
        command = limitParser.parseInput();
        assertEquals(expected4, command.getCommandInfo());

        String inputLine5 = "remaining monthly budget";
        String expected5 = "monthly budget";
        limitParser = new LimitParser(inputLine5);
        command = limitParser.parseInput();
        assertEquals(expected5, command.getCommandInfo());

        String inputLine6 = "remaining weekly saving";
        String expected6 = "weekly saving";
        limitParser = new LimitParser(inputLine6);
        command = limitParser.parseInput();
        assertEquals(expected6, command.getCommandInfo());
    }

    @Test
    public void parseInvalidInputTest() {
        LimitParser limitParser;
        Command command;

        String inputLine1 = "limit";
        limitParser = new LimitParser(inputLine1);
        command = limitParser.parseInput();
        assertEquals(expectedInvalidResult, command.getCommandInfo());

        String inputLine2 = "set budget 5";
        limitParser = new LimitParser(inputLine2);
        command = limitParser.parseInput();
        assertEquals(expectedInvalidResult, command.getCommandInfo());

        String inputLine3 = "remove five";
        limitParser = new LimitParser(inputLine3);
        command = limitParser.parseInput();
        assertEquals(expectedInvalidResult, command.getCommandInfo());

        String inputLine4 = "remaining";
        limitParser = new LimitParser(inputLine4);
        command = limitParser.parseInput();
        assertEquals(expectedInvalidResult, command.getCommandInfo());

        String inputLine5 = "";
        limitParser = new LimitParser(inputLine5);
        command = limitParser.parseInput();
        assertEquals(expectedInvalidResult, command.getCommandInfo());

        String inputLine6 = "     set     ";
        limitParser = new LimitParser(inputLine6);
        command = limitParser.parseInput();
        assertEquals(expectedInvalidResult, command.getCommandInfo());

        String inputLine7 = "hello";
        limitParser = new LimitParser(inputLine7);
        command = limitParser.parseInput();
        assertEquals(expectedInvalidResult, command.getCommandInfo());
    }
}
