package RetrieveFreeTimesTest;

import Commands.Command;
import Commands.FindFreeTimesCommand;
import Commons.UserInteraction;
import DukeExceptions.DukeInvalidFormatException;
import DukeExceptions.DukeNoValidDataException;
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

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

//@@author hwbjerry
/**
 * This class tests RetrieveFreeTimesParse.
 */
public class RetrieveFreeTimesParseTest {
    private static final String INVALID_OPTION = "Invalid option. Please enter the command as follows. \n"
            + "retrieve/time 'x', where 'x' is a digit between 1 - 5";
    private static final String INVALID_EMPTY_OPTION = "Invalid input.\n"
            + "Option cannot be blank.\nPlease enter the command as follows.\n"
            + "retrieve/time 'x', where 'x' is a digit between 1 - 5";

    private static String validUserInputWithOption;


    private static String userInputWithOptionZero;
    private static String userInputWithOptionSix;
    private static String userInputWithRandomStringOption;
    private static String userInputWithOptionInDecimal;

    private static String userInputWithoutOption;

    private static ArrayList<Pair<String, String>> retrievedFreeTimesList;

    private static TaskList events = new TaskList();
    private static TaskList deadlines = new TaskList();
    private static StorageStub storageStub = new StorageStub();
    private static UserInteraction ui = new UserInteraction();

    @BeforeAll
    public static void setAllVariables() {
        validUserInputWithOption = "retrieve/time 5";

        userInputWithOptionZero = "retrieve/time 0";
        userInputWithOptionSix = "retrieve/time 6";
        userInputWithRandomStringOption = "retrieve/time a0b1c2";
        userInputWithOptionInDecimal = "retrieve/time 1.1";

        userInputWithoutOption = "retrieve/time ";
    }

    @Before
    public static void setRetrievedFreeTimesList() {
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
    public void retrieveFreeTimesWithOptionZero() {
        setRetrievedFreeTimesList();
        String expected = INVALID_OPTION;
        String actual = null;
        Command command = null;
        try {
            command = new RetrieveFreeTimesParse(userInputWithOptionZero).parse();
        } catch (DukeInvalidFormatException | DukeNoValidDataException e) {
            actual = e.getMessage();
        }
        assertEquals(expected, actual);
    }

    @Test
    public void retrieveFreeTimesWithOptionSix() {
        setRetrievedFreeTimesList();
        String expected = INVALID_OPTION;
        String actual = null;
        Command command = null;
        try {
            command = new RetrieveFreeTimesParse(userInputWithOptionSix).parse();
        } catch (DukeInvalidFormatException | DukeNoValidDataException e) {
            actual = e.getMessage();
        }
        assertEquals(expected, actual);
    }

    @Test
    public void retrieveFreeTimesWithRandomStringOption() {
        setRetrievedFreeTimesList();
        String expected = INVALID_OPTION;
        String actual = null;
        Command command = null;
        try {
            command = new RetrieveFreeTimesParse(userInputWithRandomStringOption).parse();
        } catch (DukeInvalidFormatException | DukeNoValidDataException e) {
            actual = e.getMessage();
        }
        assertEquals(expected, actual);
    }

    @Test
    public void retrieveFreeTimesWithOptionInDecimal() {
        setRetrievedFreeTimesList();
        String expected = INVALID_OPTION;
        String actual = null;
        Command command = null;
        try {
            command = new RetrieveFreeTimesParse(userInputWithOptionInDecimal).parse();
        } catch (DukeInvalidFormatException | DukeNoValidDataException e) {
            actual = e.getMessage();
        }
        assertEquals(expected, actual);
    }

    @Test
    public void retrieveFreeTimesWithoutOption() {
        setRetrievedFreeTimesList();
        String expected = INVALID_EMPTY_OPTION;
        String actual = null;
        Command command = null;
        try {
            command = new RetrieveFreeTimesParse(userInputWithoutOption).parse();
        } catch (DukeInvalidFormatException | DukeNoValidDataException e) {
            actual = e.getMessage();
        }
        assertEquals(expected, actual);
    }

    @Test
    public void retrieveFreeTimesValidUserInputWithOption() {
        setRetrievedFreeTimesList();
        String actual = "No error";
        Command command = null;
        try {
            command = new RetrieveFreeTimesParse(validUserInputWithOption).parse();
        } catch (DukeInvalidFormatException | DukeNoValidDataException e) {
            actual = e.getMessage();
        }
        assertNotNull(command, actual);
    }


    @Test
    public void retrieveFreeTimesValidUserInputWithOptionWithoutPopulatedList() {
        String actual = "No error";
        Command command = null;
        try {
            command = new RetrieveFreeTimesParse(validUserInputWithOption).parse();
        } catch (DukeInvalidFormatException | DukeNoValidDataException e) {
            actual = e.getMessage();
        }
        assertNotNull(command, actual);
    }

}