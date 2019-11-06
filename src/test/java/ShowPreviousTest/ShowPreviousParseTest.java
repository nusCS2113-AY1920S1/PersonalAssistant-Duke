package ShowPreviousTest;

import Commands.Command;
import DukeExceptions.DukeInvalidFormatException;
import Parser.FindFreeTimesParse;
import Parser.ShowPreviousParse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;

//@@author 0325961
/**
 * This class tests ShowPreviousParse.
 */
public class ShowPreviousParseTest {

    private static String userInputWithInvalidCommandType;
    private static String userInputWithInvalidNumber;
    private static String validUserInputWithNumber;
    private static String validUserInputWithCommandType;
    private static String firstInput;
    private static String secondInput;
    private static ArrayList<String> userInputList = new ArrayList<>();

    @BeforeAll
    public static void setAllVariables() {
        userInputWithInvalidCommandType = "show/previous remove/all";
        userInputWithInvalidNumber = "show/previous -100";
        validUserInputWithNumber = "show/previous 1";
        validUserInputWithCommandType = "show/previous add/d";
        firstInput = "add/d CS2100 finish tutorial /by 12/10/2019 1300";
        secondInput = "add/d CS2100 assignment 2 /by 13/10/2019 1400";
        userInputList.add(firstInput);
        userInputList.add(secondInput);
    }

    @Test
    public void showPreviousParseWithInvalidCommandType() {
        String expected = "Invalid Input. There is no such command type in previous input";
        String actual = "";
        Command command = null;
        try {
            command = new ShowPreviousParse(userInputWithInvalidCommandType).parse();
        } catch (DukeInvalidFormatException e) {
            actual = e.getMessage();
        }
        assertEquals(expected, actual);
    }

    @Test
    public void showPreviousParseWithInvalidNumber() {
        String expected = "Invalid Input. Cannot enter negative number";
        String actual = "";
        Command command = null;
        try {
            command = new ShowPreviousParse(userInputWithInvalidNumber).parse();
        } catch (DukeInvalidFormatException e) {
            actual = e.getMessage();
        }
        assertEquals(expected, actual);
    }

    @Test
    public void showPreviousParseWithValidNumberFormat() {
        String expected = "No error";
        Command command = null;
        String actual = "";
        try {
            command = new ShowPreviousParse(validUserInputWithNumber).parse();
//            actual = command.execute(events, deadlines, ui, storageStub);
        } catch (DukeInvalidFormatException e) {
            actual = e.getMessage();
        }
        assertNotNull(expected, actual);
    }

    @Test
    public void showPreviousParseWithValidCommandType() {
        String expected = "No error";
        Command command = null;
        String actual = "";
        try {
            command = new ShowPreviousParse(validUserInputWithCommandType).parse();
        } catch (DukeInvalidFormatException e) {
            actual = e.getMessage();
        }
        assertNotNull(expected, actual);
    }
}