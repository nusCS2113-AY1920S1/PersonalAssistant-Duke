package retrievefreetimestest;

import commands.Command;
import commands.FindFreeTimesCommand;
import commons.UserInteraction;
import dukeexceptions.DukeInvalidFormatException;
import dukeexceptions.DukeNoValidDataException;
import parser.FindFreeTimesParse;
import parser.RetrieveFreeTimesParse;
import stubclasses.StorageStub;
import tasks.TaskList;
import javafx.util.Pair;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@@author hwbjerry
/**
 * This class tests RetrieveFreeTimesParse.
 */
public class RetrieveFreeTimesCommandTest {
    private static final String INVALID_NO_FREE_TIME_FOUND = "Please find free times by invoking the command shown "
            + "below\n"
            + "find/time <x> hours, where x is a digit between 1 - 16, inclusive\n"
            + "Followed by the command\n"
            + "retrieve/time <x>, where x is a digit between 1- 5, inclusive";

    private static String userInputWithValidOption;
    private static Integer userInputSelectedOption;

    private static ArrayList<Pair<String, String>> retrievedFreeTimesList;

    private TaskList events = new TaskList();
    private TaskList deadlines = new TaskList();
    private StorageStub storageStub = new StorageStub();
    private UserInteraction ui = new UserInteraction();

    /**
     * This method initializes the variables required.
     */
    @BeforeAll
    public static void setAllVariables() {
        userInputWithValidOption = "retrieve/time 3";
        userInputSelectedOption = 3;
        retrievedFreeTimesList = new ArrayList<>();
    }

    @Test
    public void retrieveFreeTimesCommandWithEmptyList() throws Exception {
        String expected = INVALID_NO_FREE_TIME_FOUND;
        String actual = null;
        Command command = null;
        try {
            command = new RetrieveFreeTimesParse(userInputWithValidOption).parse();
            actual = command.execute(events, deadlines, ui, storageStub);

        } catch (DukeInvalidFormatException | DukeNoValidDataException e) {
            actual = e.getMessage();
        }
        assertEquals(expected, actual);
    }

    /**
     * This method initializes the variables required before a test.
     */
    @Before
    public void setRetrievedFreeTimesList() {
        String actual = "No error";
        String validUserInputWithDuration = "find/time 3 hours";
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
    public void retrieveFreeTimesCommandWithPopulatedList() {
        setRetrievedFreeTimesList();

        retrievedFreeTimesList = FindFreeTimesCommand.getCompiledFreeTimesList();
        String expected = ui.showSelectionOption(userInputSelectedOption,
                retrievedFreeTimesList.get(userInputSelectedOption - 1).getKey());
        String actual = null;
        Command command = null;
        try {
            command = new RetrieveFreeTimesParse(userInputWithValidOption).parse();
            actual = command.execute(events, deadlines, ui, storageStub);
        } catch (DukeInvalidFormatException e) {
            actual = e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(expected, actual);
        clearParameter();
    }

    @After
    public void clearParameter() {
        retrievedFreeTimesList.clear();
    }
}
