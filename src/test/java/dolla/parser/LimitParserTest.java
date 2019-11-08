package dolla.parser;

import dolla.command.Command;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@@author Weng-Kexin
public class LimitParserTest {

    private static final String expectedCommandInfo = "limit";
    private static final String expectedInvalidResult = "ErrorCommand";

    @Test
    public void parseValidListInputTest() {
        String inputLine = "limits";
        LimitParser limitParser = new LimitParser(inputLine);
        Command command = limitParser.parseInput();
        assertEquals(expectedCommandInfo, command.getCommandInfo());
    }

    @Test
    public void parseInvalidListInputTest() {
        String inputLine = "limit";
        LimitParser limitParser = new LimitParser(inputLine);
        Command command = limitParser.parseInput();
        assertEquals(expectedInvalidResult, command.getCommandInfo());
    }

    @Test
    public void parseValidSetFormatTest() {
        String inputLine = "set budget 10 daily";
        String expected = "budget 10.0 daily";
        LimitParser limitParser = new LimitParser(inputLine);
        Command command = limitParser.parseInput();
        assertEquals(expected, command.getCommandInfo());
    }

    @Test
    public void parseInvalidSetFormatTest() {
        String inputLine = "set budget 5";
        LimitParser limitParser = new LimitParser(inputLine);
        Command command = limitParser.parseInput();
        assertEquals(expectedInvalidResult, command.getCommandInfo());
    }

    @Test
    public void parseValidRemoveTest() {
        String inputLine = "remove 1";
        LimitParser limitParser = new LimitParser(inputLine);
        Command command = limitParser.parseInput();
        assertEquals(expectedCommandInfo, command.getCommandInfo());
    }

    @Test
    public void parseInvalidRemoveTest() {
        String inputLine = "remove five";
        LimitParser limitParser = new LimitParser(inputLine);
        Command command = limitParser.parseInput();
        assertEquals(expectedInvalidResult, command.getCommandInfo());
    }

    @Test
    public void parseInvalidShowTest() {
        String inputLine = "remaining";
        LimitParser limitParser = new LimitParser(inputLine);
        Command command = limitParser.parseInput();
        assertEquals(expectedInvalidResult, command.getCommandInfo());
    }

    @Test
    public void parseValidShowTest() {
        String inputLine = "remaining daily budget";
        String expected = "remaining daily";
        LimitParser limitParser = new LimitParser(inputLine);
        Command command = limitParser.parseInput();
        assertEquals(expected, command.getCommandInfo());
    }

    @Test
    public void parseInvalidInputTest1() {
        String inputLine = "";
        LimitParser limitParser = new LimitParser(inputLine);
        Command command = limitParser.parseInput();
        assertEquals(expectedInvalidResult, command.getCommandInfo());
    }

    @Test
    public void parseInvalidInputTest2() {
        String inputLine = "     set     ";
        LimitParser limitParser = new LimitParser(inputLine);
        Command command = limitParser.parseInput();
        assertEquals(expectedInvalidResult, command.getCommandInfo());
    }

    @Test
    public void parseInvalidInputTest3() {
        String inputLine = "hello";
        LimitParser limitParser = new LimitParser(inputLine);
        Command command = limitParser.parseInput();
        assertEquals(expectedInvalidResult, command.getCommandInfo());
    }
}
