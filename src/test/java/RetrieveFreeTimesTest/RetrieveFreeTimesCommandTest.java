package RetrieveFreeTimesTest;

import Commands.Command;
import Commands.FindFreeTimesCommand;
import Commons.UserInteraction;
import DukeExceptions.DukeInvalidFormatException;
import Parser.FindFreeTimesParse;
import Parser.RetrieveFreeTimesParse;
import StubClasses.StorageStub;
import Tasks.TaskList;
import javafx.util.Pair;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterAll;
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

    private static String userInputWithValidOption;
    private static Integer userInputSelectedOption;

    private static ArrayList<Pair<String, String>> retrievedFreeTimesList;

    private TaskList events = new TaskList();
    private TaskList deadlines = new TaskList();
    private StorageStub storageStub = new StorageStub();
    private UserInteraction ui = new UserInteraction();

    @BeforeAll
    public static void setAllVariables() {
        userInputWithValidOption = "retrieve/time 3";
        userInputSelectedOption = 3;
        retrievedFreeTimesList = new ArrayList<>();
    }

    @Test
    public void retrieveFreeTimesCommandWithEmptyList() throws Exception {
        String expected = ui.showSelectionOptionEmptyList();
        String actual = null;
        Command command = null;
        try {
            command = new RetrieveFreeTimesParse(userInputWithValidOption).parse();
            actual = command.execute(events, deadlines, ui, storageStub);

        } catch (DukeInvalidFormatException e) {
            actual = e.getMessage();
        }
        assertEquals(expected, actual);
    }

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
        String expected = ui.showSelectionOption(userInputSelectedOption, retrievedFreeTimesList.get(userInputSelectedOption-1).getKey());
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
    public void clearParameter(){
        retrievedFreeTimesList.clear();
    }
}
