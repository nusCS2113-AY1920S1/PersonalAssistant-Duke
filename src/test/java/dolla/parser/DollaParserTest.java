package dolla.parser;

import dolla.command.Command;
import dolla.exception.DollaException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@@author omupenguin
public class DollaParserTest {

    private String inputLine;
    private String expectedResult;

    //Private DollaParser createNewDollaParser(String inputLine) {
    //    return new DollaParser(inputLine);
    //}

    @Test
    public void parseInputAddSuccess() throws DollaException {
        inputLine = "add expense 100 Expense Description /on 03/12/2001";
        DollaParser dollaParser = new DollaParser(inputLine);
        //expectedResult = "AddEntryCommand{ type: expense, amount: 100.0, " +
        //        "description: Expense Description, date: 03/12/2001, prePosition: -1 }";
        expectedResult = "AddEntryCommand{ expense, 100.0, Expense Description, 03/12/2001}";
        Command command = dollaParser.parseInput();
        assertEquals(expectedResult, command.getCommandInfo());
    }

    @Test
    public void parseInputAddInvalidInput() throws DollaException {
        inputLine = "add potatoes /on 03/12/2001";
        DollaParser dollaParser = new DollaParser(inputLine);
        expectedResult = "ErrorCommand";
        Command command = dollaParser.parseInput();
        assertEquals(expectedResult, command.getCommandInfo());
    }

}
