package ShowPreviousTest;

import Commands.Command;
import Commands.ShowPreviousCommand;
import Commons.LookupTable;
import Commons.Ui;
import StubClasses.StorageStub;
import Tasks.TaskList;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@@author 0325961
/**
 * This class tests ShowPreviousCommand.
 */
public class ShowPreviousCommandTest {
    private static String userInputWithInvalidNumber;
    private static ArrayList<String> userInputList = new ArrayList<>();
    private static int sizeOfList;
    private LookupTable lookupTable = new LookupTable();
    private TaskList events = new TaskList();
    private TaskList deadlines = new TaskList();
    private StorageStub storageStub = new StorageStub();
    private Ui ui = new Ui();


    @BeforeAll
    public static void setAllVariables() {
        userInputWithInvalidNumber = "show/previous 3";
        String firstInput = "add/d CS2100 finish tutorial /by 12/10/2019 1300";
        String secondInput = "add/d CS2100 assignment 2 /by 13/10/2019 1400";
        userInputList.add(firstInput);
        userInputList.add(secondInput);
        sizeOfList = userInputList.size();
    }

    @Test
    public void showPreviousCommandTestWithInvalidNumber() {
        userInputWithInvalidNumber = userInputWithInvalidNumber.replaceFirst("show/previous", "");
        String showPreviousNumber = userInputWithInvalidNumber.trim();
        Command command = new ShowPreviousCommand(showPreviousNumber);
        String expected = "There are only " + sizeOfList + " previous commands. Please enter a valid number less than or equal to " + sizeOfList + ".";
        String actual = "";
        try {
            actual = command.execute(lookupTable, events, deadlines, ui, storageStub);
        } catch (Exception e) {
            actual = e.getMessage();
        }
        assertEquals(expected, actual);
    }
}
