package integration;

import org.junit.jupiter.api.Test;
import spinbox.Parser;
import spinbox.Ui;
import spinbox.commands.Command;
import spinbox.containers.ModuleContainer;
import spinbox.containers.Notepad;
import spinbox.entities.Module;

import spinbox.exceptions.CorruptedDataException;
import spinbox.exceptions.DataReadWriteException;
import spinbox.exceptions.FileCreationException;
import spinbox.exceptions.InputException;
import spinbox.exceptions.InvalidIndexException;
import spinbox.exceptions.SpinBoxException;

import java.util.ArrayDeque;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class NotepadIntegrationTest {
    @Test
    public void loadDataSuccessful_AddLinesThenManualClear_successfulRepopulationOfData() throws
            DataReadWriteException, FileCreationException, CorruptedDataException {
        ModuleContainer testContainer = new ModuleContainer();
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
        ModuleContainer testContainer = new ModuleContainer();
        Module testModule = new Module("CG1112", "Engineering Principles & Practice III");
        testContainer.addModule(testModule);

        Notepad testPad = testModule.getNotepad();
        List<String> notes = testPad.getNotes();
        while (notes.size() > 0) {
            testPad.removeLine(0);
        }
        testPad.addLine("test line0");

        ArrayDeque<String> pageTrace = new ArrayDeque<>();
        Ui ui = new Ui(true);

        String setNameForNote1 = "set-name CG1112 / note 1 to: test line1";
        Parser.setPageTrace(pageTrace);
        Command command = Parser.parse(setNameForNote1);
        command.execute(testContainer, pageTrace, ui, false);

        assertEquals(testPad.getLine(0), "test line1");
    }

    @Test
    public void setNameUnsuccessful_invalidIndexUsed_exceptionThrown() throws
            SpinBoxException {
        ModuleContainer testContainer = new ModuleContainer();
        Module testModule = new Module("CG1112", "Engineering Principles & Practice III");
        testContainer.addModule(testModule);

        Notepad testPad = testModule.getNotepad();
        List<String> notes = testPad.getNotes();
        while (notes.size() > 0) {
            testPad.removeLine(0);
        }
        testPad.addLine("test line0");

        ArrayDeque<String> pageTrace = new ArrayDeque<>();
        Ui ui = new Ui(true);

        try {
            String setNameForNote1 = "set-name CG1112 / note 2 to: test line1";
            Parser.setPageTrace(pageTrace);
            Command command = Parser.parse(setNameForNote1);
            command.execute(testContainer, pageTrace, ui, false);
            fail();
        } catch (InvalidIndexException e) {
            assertEquals("Invalid Input\n\nYou have entered an invalid index.", e.getMessage());
        }
    }

    @Test
    public void setDateUnsuccessful_setDateUsedOnNonSchedulableItems_exceptionThrown() throws
            SpinBoxException {
        ModuleContainer testContainer = new ModuleContainer();
        Module testModule = new Module("CG1112", "Engineering Principles & Practice III");
        testContainer.addModule(testModule);

        Notepad testPad = testModule.getNotepad();
        List<String> notes = testPad.getNotes();
        while (notes.size() > 0) {
            testPad.removeLine(0);
        }
        testPad.addLine("test line0");

        ArrayDeque<String> pageTrace = new ArrayDeque<>();
        Ui ui = new Ui(true);

        try {
            String setDateForNote1 = "set-date CG1112 / note 1 to: tomorrow";
            Parser.setPageTrace(pageTrace);
            Command command = Parser.parse(setDateForNote1);
            command.execute(testContainer, pageTrace, ui, false);
            fail();
        } catch (InputException e) {
            assertEquals("Invalid Input\n\nSorry, set-date is not available for the selected tab.",
                    e.getMessage());
        }
    }
}
