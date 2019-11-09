package dolla.parser;

import dolla.command.Command;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@@author tatayu
public class DebtParserTest {

    private String inputLine;
    private String expected;

    @Test
    public void parseValidListInputTest() {
        inputLine = "debts";
        DebtsParser debtsParser = new DebtsParser(inputLine);
        Command command = debtsParser.parseInput();
        expected = "debt";
        assertEquals(expected, command.getCommandInfo());
    }

    @Test
    public void parseInvalidListInputTest() {
        inputLine = "debt";
        DebtsParser debtsParser = new DebtsParser(inputLine);
        Command command = debtsParser.parseInput();
        expected = "ErrorCommand";
        assertEquals(expected, command.getCommandInfo());
    }

    @Test
    public void parseValidOweCommand() {
        inputLine = "owe tata 10 food /due 12/11/2019";
        DebtsParser debtsParser = new DebtsParser(inputLine);
        expected = "owe tata 10.0 food 2019-11-12";
        Command command = debtsParser.parseInput();
        assertEquals(expected, command.getCommandInfo());
    }

    @Test
    public void parseInvalidOweCommand() {
        inputLine = "owe tata";
        DebtsParser debtsParser = new DebtsParser(inputLine);
        Command command = debtsParser.parseInput();
        expected = "ErrorCommand";
        assertEquals(expected, command.getCommandInfo());
    }

    @Test
    public void parseValidBorrowCommand() {
        inputLine = "borrow tata 10 food /due 12/11/2019";
        DebtsParser debtsParser = new DebtsParser(inputLine);
        expected = "borrow tata 10.0 food 2019-11-12";
        Command command = debtsParser.parseInput();
        assertEquals(expected, command.getCommandInfo());
    }

    @Test
    public void parseInvalidBorrowCommand() {
        inputLine = "borrow tata";
        DebtsParser debtsParser = new DebtsParser(inputLine);
        Command command = debtsParser.parseInput();
        expected = "ErrorCommand";
        assertEquals(expected, command.getCommandInfo());
    }

    @Test
    public void parseValidSearchCommand() {
        inputLine = "search name tata";
        DebtsParser debtsParser = new DebtsParser(inputLine);
        Command command = debtsParser.parseInput();
        expected = "name tata";
        assertEquals(expected, command.getCommandInfo());
    }

    @Test
    public void parseInvalidSearchCommand() {
        inputLine = "search tata";
        DebtsParser debtsParser = new DebtsParser(inputLine);
        Command command = debtsParser.parseInput();
        expected = "ErrorCommand";
        assertEquals(expected, command.getCommandInfo());
    }

    @Test
    public void parseValidRemoveCommand() {
        inputLine = "remove 3";
        DebtsParser debtsParser = new DebtsParser(inputLine);
        Command command = debtsParser.parseInput();
        expected = "debt";
        assertEquals(expected, command.getCommandInfo());
    }

    @Test
    public void parseInvalidRemoveCommand() {
        String inputLine = "remove three";
        DebtsParser debtsParser = new DebtsParser(inputLine);
        Command command = debtsParser.parseInput();
        expected = "ErrorCommand";
        assertEquals(expected, command.getCommandInfo());
    }

    @Test
    public void parseInvalidInputCommand() {
        String inputLine = "";
        DebtsParser debtsParser = new DebtsParser(inputLine);
        Command command = debtsParser.parseInput();
        expected = "ErrorCommand";
        assertEquals(expected, command.getCommandInfo());
    }
}
