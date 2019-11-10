package ShowPreviousTest;

import Commands.Command;
import Commands.ShowPreviousCommand;
import Commons.Duke;
import Commons.UserInteraction;
import DukeExceptions.DukeInvalidFormatException;
import Parser.FindFreeTimesParse;
import StubClasses.StorageStub;
import Tasks.TaskList;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * This class tests ShowPreviousCommand.
 */
public class ShowPreviousCommandTest {
    private static String userInputWithInvalidNumber;
    private static ArrayList<String> userInputList = new ArrayList<>();

    private static int sizeOfList;
    private TaskList events = new TaskList();
    private TaskList deadlines = new TaskList();
    private StorageStub storageStub = new StorageStub();
    private UserInteraction ui = new UserInteraction();


    /**
     * This method initializes the variable required.
     */
    @BeforeAll
    public static void setAllVariables() {
        userInputWithInvalidNumber = "show/previous 3";
    }

    /**
     * This method initializes the variables required before a test.
     */
    @Before
    public void setRetrievedFreeTimesList() {
        String actual = "No error";
        String validUserInputWithDuration = "find 3 hours";
        Command command = null;
        try {
            command = new FindFreeTimesParse(validUserInputWithDuration).parse();
            actual = command.execute(events, deadlines, ui, storageStub);
        } catch (DukeInvalidFormatException e) {
            actual = e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertNotNull(command, actual);
    }

    @Test
    public void showPreviousCommand_UserInputWithInvalidNumber_throwsDukeInvalidCommandexception() {
        String firstInput = "add/d CS2100 finish tutorial /by 12/10/2019 1300";
        String secondInput = "add/d CS2100 assignment 2 /by 13/10/2019 1400";
        String weekInput = "Week 6";
        Duke.userInputs.add(firstInput);
        Duke.userInputs.add(weekInput);
        Duke.userInputs.add(secondInput);
        Duke.userInputs.add(weekInput);
        Duke.userInputs.add(secondInput);
        Duke.userInputs.add(weekInput);
        sizeOfList = Duke.getUserInputs().size() / 2;

        String t = userInputWithInvalidNumber.replaceFirst("show/previous", "");
        String showPreviousNumber = t.trim();
        Command command = new ShowPreviousCommand(showPreviousNumber);
        String expected = "There are only " + (sizeOfList - 1) + " previous commands. Please enter a valid number "
                + "less than or equal to " + (sizeOfList - 1) + " .";
        String actual = "";
        try {
            actual = command.execute(events, deadlines, ui, storageStub);
        } catch (Exception e) {
            actual = e.getMessage();
        }
        assertEquals(expected, actual);
    }
}