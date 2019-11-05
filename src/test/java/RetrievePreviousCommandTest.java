 import Commands.Command;
import Commands.RetrievePreviousCommand;
import Commons.LookupTable;
import Commons.UserInteraction;
import StubClasses.StorageStub;
import Tasks.TaskList;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RetrievePreviousCommandTest {
    private static ArrayList<String> previousInputList;
    private static int sizeOfList;
    private static String userInputWithInvalidNumber;
    private static String userInputToGetFromEmptyPreviousInputList;
    private static String userInputWithValidNumber;
    private static String userInputToGetFromNonEmptyPreviousInputList;
    private TaskList events = new TaskList();
    private TaskList deadlines = new TaskList();
    private StorageStub storageStub = new StorageStub();
    private UserInteraction ui = new UserInteraction();

    @BeforeAll
    public static void setAllVariables() {
        String firstPreviousInput = "add/d CS2100 finish tutorial /by 12/10/2019 1300";
        String secondPreviousInput = "add/d CS2100 assignment 2 /by 13/10/2019 1400";
        previousInputList.add(firstPreviousInput);
        previousInputList.add(secondPreviousInput);
        userInputWithInvalidNumber = "retrieve/previous 3";
        userInputToGetFromEmptyPreviousInputList = "retrieve/previous 1";
        userInputWithValidNumber = "retrieve/previous 2";
        userInputToGetFromNonEmptyPreviousInputList = "retrieve/previous 1";
        sizeOfList = previousInputList.size();
    }

    @Test
    public void retrievePreviousCommandTestWithInvalidNUmber() {
        Command command = new RetrievePreviousCommand(userInputWithInvalidNumber);
        String expected = "There are only " + sizeOfList + " of previous commands." +
                "Please enter a valid number less than or equal to " + sizeOfList + " .";
        String actual = "";
        try {
            actual = command.execute(events, deadlines, ui, storageStub);
        } catch (Exception e) {
            actual = e.getMessage();
        }
        assertEquals(expected, actual);
    }

    @Test
    public void retrievePreviousCommandTestWithEmptyPreviousInputList() {
        Command command = new RetrievePreviousCommand(userInputToGetFromEmptyPreviousInputList);
        String expected = "You did not enter Show Previous Command yet. \n" +
                "Format: show previous <num> or show previous <type> <num>";
        String actual = "";
        try {
            actual = command.execute(events, deadlines, ui, storageStub);
        } catch (Exception e) {
            actual = e.getMessage();
        }
        assertEquals(expected, actual);
    }

    @Test
    public void retrievePreviousCommandTestWithValidNumber() {
        Command command = new RetrievePreviousCommand(userInputWithValidNumber);
        String expected = "Your chosen previous input is: \n add/d CS2100 assignment 2 /by 13/10/2019 1400";
        String actual = "";
        try {
            actual = command.execute(events, deadlines, ui, storageStub);
        } catch (Exception e) {
            actual = e.getMessage();
        }
        assertEquals(expected, actual);
    }

    @Test
    public void retrievePreviousCommandTestWithNonEmptyList() {
        Command command = new RetrievePreviousCommand(userInputToGetFromNonEmptyPreviousInputList);
        String expected = "Your chosen previous input is: \n add/d CS2100 finish tutorial /by 12/10/2019 1300";
        String actual = "";
        try {
            actual = command.execute(events, deadlines, ui, storageStub);
        } catch (Exception e) {
            actual = e.getMessage();
        }
        assertEquals(expected, actual);
    }
}

