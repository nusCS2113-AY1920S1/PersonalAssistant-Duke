package dolla.command;

import dolla.parser.ParserStringList;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddDebtCommandTest implements ParserStringList {

    @Test
    public void addDebtCommandTest1() {
        Command commandTest = new AddDebtsCommand(DEBT_COMMAND_OWE, "tata", 10, "food", LocalDate.parse("2019-11-12"));
        String expected = "owe tata 10.0 food 2019-11-12";
        assertEquals(expected, commandTest.getCommandInfo());
    }

    @Test
    public void addDebtCommandTest2() {
        Command commandTest = new AddDebtsCommand(DEBT_COMMAND_BORROW, "xx", 200, "fish", LocalDate.parse("2019-11-11"));
        String expected = "borrow xx 200.0 fish 2019-11-11";
        assertEquals(expected, commandTest.getCommandInfo());
    }
}
