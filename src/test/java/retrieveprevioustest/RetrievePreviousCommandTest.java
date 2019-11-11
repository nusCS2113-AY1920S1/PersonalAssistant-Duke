package retrieveprevioustest;

import commands.Command;
import commands.RetrievePreviousCommand;
import commands.ShowPreviousCommand;
import commons.UserInteraction;
import dukeexceptions.DukeInvalidFormatException;
import parser.FindFreeTimesParse;
import parser.ShowPreviousParse;
import parser.WeekParse;
import stubclasses.StorageStub;
import tasks.TaskList;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * This class tests RetrievePreviousCommand.
 */
public class RetrievePreviousCommandTest {
    private static ArrayList<String> previousInputList;
    private static String userInputWithInvalidNumberGreaterThanSize;
    private static String userInputWithValidNumber;
    private static String userInputToGetFromNonEmptyPreviousInputList;
    private static String userInputWithoutInteger;
    private static String userInputWithString;
    private static String userInputWithNumberZero;
    private static String userInputWithNegativeNumber;
    private TaskList events = new TaskList();
    private TaskList deadlines = new TaskList();
    private StorageStub storageStub = new StorageStub();
    private UserInteraction ui = new UserInteraction();

    /**
     * This method initializes the variables required.
     */
    @BeforeAll
    public static void setAllVariables() {
        userInputWithInvalidNumberGreaterThanSize = "retrieve/previous 3";
        userInputWithValidNumber = "retrieve/previous 2";
        userInputToGetFromNonEmptyPreviousInputList = "retrieve/previous 1";
        userInputWithoutInteger = "retrieve/previous";
        userInputWithString = "retrieve/previous abc";
        userInputWithNumberZero = "retrieve/previous 0";
        userInputWithNegativeNumber = "retrieve/previous -10";
    }

    /**
     * This method initializes the variables required before a test.
     */
    @Before
    public void runWeekCommand() {
        String actual = "No error";
        String validUserInputWithDuration = "show/week 3";
        Command command = null;
        try {
            command = new WeekParse(validUserInputWithDuration).parse();
            actual = command.execute(events, deadlines, ui, storageStub);
        } catch (DukeInvalidFormatException e) {
            actual = e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertNotNull(command, actual);
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

    /**
     * This method initializes the variables required before a test.
     */
    @Before
    public void showPreviousCommandList() {
        setRetrievedFreeTimesList();
        runWeekCommand();
        setRetrievedFreeTimesList();
        runWeekCommand();
        String actual = "No error";
        String validUserInput = "show/previous 2";
        Command command = null;
        try {
            command = new ShowPreviousParse(validUserInput).parse();
            actual = command.execute(events, deadlines, ui, storageStub);
        } catch (DukeInvalidFormatException e) {
            actual = e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertNotNull(command, actual);
    }

    public ArrayList<String> addingToPreviousInputList(ArrayList<String> previousInputList) {
        previousInputList.add("find/time 3 hours");
        previousInputList.add("find/time 3 hours");
        return previousInputList;
    }
    @Test
    public void retrievePreviousCommand_userInputWithInvalidNumberGreaterThanSize_throwsDukeInvalidCommandException() {
        showPreviousCommandList();
        runWeekCommand();

        previousInputList = ShowPreviousCommand.getOutputList();
        addingToPreviousInputList(previousInputList);
        int sizeOfList = previousInputList.size();
        Command command = new RetrievePreviousCommand(userInputWithInvalidNumberGreaterThanSize);
        String expected = "There are only " + sizeOfList + " of previous commands."
                + "Please enter a valid number less than or equal to " + sizeOfList + " but greater than 0.";
        String actual = "";
        try {
            actual = command.execute(events, deadlines, ui, storageStub);
        } catch (Exception e) {
            actual = e.getMessage();
        }
        assertEquals(expected, actual);
    }

    @Test
    public void retrievePreviousCommand_userInputWithoutInteger_throwsDukeInvalidCommandException() {
        Command command = new RetrievePreviousCommand(userInputWithoutInteger);
        String expected = "<x> cannot be empty. Please enter the valid command as retrieve/previous <x>, "
                + "where x is an integer.";
        String actual = "";
        try {
            actual = command.execute(events, deadlines, ui, storageStub);
        } catch (Exception e) {
            actual = e.getMessage();
        }
        assertEquals(expected, actual);
    }

    @Test
    public void retrievePreviousCommand_userInputWithString_throwsDukeInvalidCommandException() {
        showPreviousCommandList();
        runWeekCommand();

        Command command = new RetrievePreviousCommand(userInputWithString);
        String expected = "The x in retrieve/previous <x> must be an integer and not a string.";
        String actual = "";
        try {
            actual = command.execute(events, deadlines, ui, storageStub);
        } catch (Exception e) {
            actual = e.getMessage();
        }
        assertEquals(expected, actual);
    }

    @Test
    public void retrievePreviousCommand_userInputWithNumberZero_throwsDukeInvalidCommandException() {
        showPreviousCommandList();
        runWeekCommand();

        previousInputList = ShowPreviousCommand.getOutputList();
        int sizeOfList = previousInputList.size();
        Command command = new RetrievePreviousCommand(userInputWithNumberZero);
        String expected = "Please enter a valid integer x from 1 to " + sizeOfList + " .";
        String actual = "";
        try {
            actual = command.execute(events, deadlines, ui, storageStub);
        } catch (Exception e) {
            actual = e.getMessage();
        }
        assertEquals(expected, actual);
    }

    @Test
    public void retrievePreviousCommand_userInputWithNegativeNumber_throwsDukeInvalidCommandException() {
        showPreviousCommandList();
        runWeekCommand();

        previousInputList = ShowPreviousCommand.getOutputList();
        int sizeOfList = previousInputList.size();
        Command command = new RetrievePreviousCommand(userInputWithNegativeNumber);
        String expected = "Please enter a valid integer x from 1 to " + sizeOfList + " .";
        String actual = "";
        try {
            actual = command.execute(events, deadlines, ui, storageStub);
        } catch (Exception e) {
            actual = e.getMessage();
        }
        assertEquals(expected, actual);
    }

    @Test
    public void retrievePreviousCommand_UserInputWithValidNumber() {
        showPreviousCommandList();
        runWeekCommand();

        Command command = new RetrievePreviousCommand(userInputWithValidNumber);
        previousInputList = ShowPreviousCommand.getOutputList();
        int index = 1;
        String chosenInput = previousInputList.get(index);
        String expected = "Your chosen previous input is: \n" + chosenInput;
        String actual = "";
        try {
            actual = command.execute(events, deadlines, ui, storageStub);
        } catch (Exception e) {
            actual = e.getMessage();
        }
        assertEquals(expected, actual);
    }

    @Test
    public void retrievePreviousCommand_UserInputWithNonEmptyList() {
        showPreviousCommandList();
        runWeekCommand();

        Command command = new RetrievePreviousCommand(userInputToGetFromNonEmptyPreviousInputList);
        previousInputList = ShowPreviousCommand.getOutputList();
        int index = 0;
        String chosenInput = previousInputList.get(index);
        String expected = ui.showChosenPreviousChoice(chosenInput);
        String actual = "";
        try {
            actual = command.execute(events, deadlines, ui, storageStub);
        } catch (Exception e) {
            actual = e.getMessage();
        }
        assertEquals(expected, actual);
    }
}

