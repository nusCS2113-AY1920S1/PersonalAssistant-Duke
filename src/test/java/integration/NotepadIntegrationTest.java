package integration;

import org.junit.jupiter.api.Test;
import spinbox.Parser;
import spinbox.Ui;
import spinbox.commands.Command;
import spinbox.containers.ModuleContainer;
import spinbox.containers.Notepad;
import spinbox.entities.Module;

import spinbox.exceptions.DataReadWriteException;
import spinbox.exceptions.InputException;
import spinbox.exceptions.InvalidIndexException;
import spinbox.exceptions.SpinBoxException;
import spinbox.exceptions.FileCreationException;
import spinbox.exceptions.CorruptedDataException;
import spinbox.exceptions.DateFormatException;

import java.util.ArrayDeque;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class NotepadIntegrationTest {
    private ModuleContainer testContainer;
    private Module testModule;
    private Notepad testPad;
    private ArrayDeque<String> pageTrace;
    private Ui ui;

    /**
     * Clears the noteList of any notes that are stored from previous tests.
     * @param notes The noteList that is used to store notes from tests.
     * @throws DataReadWriteException If there is an error reading/writing to the files.
     * @throws InvalidIndexException If an invalid index is entered.
     */
    private void clearNotesList(List<String> notes) throws DataReadWriteException, InvalidIndexException {
        while (notes.size() > 0) {
            testPad.removeLine(0);
        }
    }

    /**
     * Initialize the set up which creates a test module and adds the test module into the module container,
     * as well as add a new note into the noteList under the test module.
     * @throws FileCreationException If there is an error when creating new files.
     * @throws DataReadWriteException If there is an error reading/writing to the files.
     * @throws CorruptedDataException If the data within the files have been corrupted.
     * @throws DateFormatException If an invalid date format is provided.
     * @throws InvalidIndexException If an invalid index is entered.
     */
    private void initializeSetUp() throws FileCreationException, DataReadWriteException, CorruptedDataException,
            DateFormatException, InvalidIndexException {
        testContainer = new ModuleContainer();
        testModule = new Module("TESTMOD", "Test Module");
        testContainer.addModule(testModule);

        testPad = testModule.getNotepad();
        List<String> notes = testPad.getNotes();
        clearNotesList(notes);
        testPad.addLine("test line0");

        pageTrace = new ArrayDeque<>();
        ui = new Ui(true);
    }

    /**
     * Executes the command that is provided to the Parser.
     * @param userInput The String input provided to the test.
     * @throws SpinBoxException If there are storage errors or input errors.
     */
    private void executeCommand(String userInput) throws SpinBoxException {
        Parser.setPageTrace(pageTrace);
        Command command = Parser.parse(userInput);
        command.execute(testContainer, pageTrace, ui, false);
    }

    @Test
    public void loadDataSuccessful_AddLinesThenManualClear_successfulRepopulationOfData() throws
            DataReadWriteException, FileCreationException, CorruptedDataException {
        Notepad testPad = new Notepad("notesTest5");
        testPad.addLine("test line0");
        testPad.addLine("test line1");
        testPad.addLine("test line2");
        testPad.addLine("test line3");

        testPad.getNotes().clear();
        testPad.loadData();

        assertEquals(testPad.getNotes().get(0), "test line0");
        assertEquals(testPad.getNotes().get(1), "test line1");
        assertEquals(testPad.getNotes().get(2), "test line2");
        assertEquals(testPad.getNotes().get(3), "test line3");
    }

    @Test
    public void setNameSuccessful_setNameOfNoteToANewName_noteDescriptionSuccessfullySet() throws
            SpinBoxException {
        initializeSetUp();

        String setNameForNote1 = "set-name TESTMOD / note 1 to: test line1";
        executeCommand(setNameForNote1);

        assertEquals(testPad.getLine(0), "test line1");
        testContainer.removeModule(testModule.getModuleCode(),testModule);
    }

    @Test
    public void setNameUnsuccessful_invalidIndexUsed_exceptionThrown() throws
            SpinBoxException {
        initializeSetUp();

        try {
            String setNameForNote1 = "set-name TESTMOD / note 2 to: test line1";
            executeCommand(setNameForNote1);
            fail();
        } catch (InvalidIndexException e) {
            testContainer.removeModule(testModule.getModuleCode(),testModule);
            assertEquals("Invalid Input\n\nYou have entered an invalid index.", e.getMessage());
        }
    }

    @Test
    public void setDateUnsuccessful_setDateUsedOnNonSchedulableItems_exceptionThrown() throws
            SpinBoxException {
        initializeSetUp();

        try {
            String setDateForNote1 = "set-date TESTMOD / note 1 to: tomorrow";
            executeCommand(setDateForNote1);
            fail();
        } catch (InputException e) {
            testContainer.removeModule(testModule.getModuleCode(),testModule);
            assertEquals("Invalid Input\n\nSorry, set-date is not available for the selected tab.",
                    e.getMessage());
        }
    }
}