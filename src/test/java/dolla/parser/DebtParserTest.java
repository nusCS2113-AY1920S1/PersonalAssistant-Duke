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

    @Test
    public void parseValidSortTest1() {
        inputLine = "sort amount";
        DebtsParser debtsParser = new DebtsParser(inputLine);
        Command command = debtsParser.parseInput();
        expected = "sort amount in debt";
        assertEquals(expected, command.getCommandInfo());
    }

    @Test
    public void parseValidSortTest2() {
        inputLine = "sort date";
        DebtsParser debtsParser = new DebtsParser(inputLine);
        Command command = debtsParser.parseInput();
        expected = "sort date in debt";
        assertEquals(expected, command.getCommandInfo());
    }

    @Test
    public void parseValidSortTest3() {
        inputLine = "sort name";
        DebtsParser debtsParser = new DebtsParser(inputLine);
        Command command = debtsParser.parseInput();
        expected = "sort name in debt";
        assertEquals(expected, command.getCommandInfo());
    }

    @Test
    public void parseValidSortTest4() {
        inputLine = "sort description";
        DebtsParser debtsParser = new DebtsParser(inputLine);
        Command command = debtsParser.parseInput();
        expected = "sort description in debt";
        assertEquals(expected, command.getCommandInfo());
    }

    @Test
    public void parseInvalidSortTest() {
        inputLine = "sort random";
        DebtsParser debtsParser = new DebtsParser(inputLine);
        Command command = debtsParser.parseInput();
        expected = "ErrorCommand";
        assertEquals(expected, command.getCommandInfo());
    }
}
