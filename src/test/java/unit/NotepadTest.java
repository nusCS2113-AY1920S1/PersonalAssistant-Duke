package unit;

import org.junit.jupiter.api.Test;
import spinbox.containers.ModuleContainer;
import spinbox.containers.Notepad;
import spinbox.exceptions.CorruptedDataException;
import spinbox.exceptions.DataReadWriteException;
import spinbox.exceptions.FileCreationException;
import spinbox.exceptions.InvalidIndexException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class NotepadTest {
    private ModuleContainer testContainer;

    public NotepadTest() throws CorruptedDataException, FileCreationException, DataReadWriteException {
        testContainer = new ModuleContainer();
    }

    private void populateLines(Notepad notepad) throws DataReadWriteException {
        notepad.addLine("test line0");
        notepad.addLine("test line1");
        notepad.addLine("test line2");
        notepad.addLine("test line3");
    }

    @Test
    public void notepadCreation_oneNewNotepad_successfulCreationAndWorkingGetters() throws
            FileCreationException {
        Notepad testPad = new Notepad("notesTest1");
        assertEquals(testPad.getNotes().size(),0);
    }

    @Test
    public void notepadAddLine_addNewLineToNewNotepad_successfulAddition() throws FileCreationException,
            DataReadWriteException, CorruptedDataException {
        Notepad testPad = new Notepad("notesTest2");
        testPad.addLine("test line");
        assertEquals(testPad.getNotes().get(0), "test line");
    }

    @Test
    public void notepadUpdateLine_AddThenUpdateLineInNewNotepad_successfulUpdateAndIndexException() throws
            DataReadWriteException, InvalidIndexException, FileCreationException, CorruptedDataException {
        Notepad testPad = new Notepad("notesTest3");
        testPad.addLine("test line");

        testPad.updateLine(0, "test line2");
        assertEquals(testPad.getNotes().get(0), "test line2");
        assertThrows(InvalidIndexException.class, () -> {
            testPad.updateLine(3, "test line3");
        });
        assertEquals(testPad.getNotes().get(0), "test line2");
    }

    @Test
    public void notepadRemoveLine_AddMultipleLinesThenRemoveOne_successfulRemoveAndIndexException() throws
            InvalidIndexException, DataReadWriteException, FileCreationException, CorruptedDataException {
        Notepad testPad = new Notepad("notesTest4");
        populateLines(testPad);
        testPad.removeLine(2);
        assertEquals(testPad.getNotes().size(), 3);
        assertEquals(testPad.getNotes().get(2), "test line3");
        assertThrows(InvalidIndexException.class, () -> {
            testPad.removeLine(3);
        });
    }

    @Test
    public void notepadGetLine_AddMultipleLinesThenRetrieveOne_successfulRetrieveAndIndexException()
            throws DataReadWriteException, FileCreationException, InvalidIndexException {
        Notepad testPad = new Notepad("notesTest5");
        populateLines(testPad);
        assertEquals(testPad.getLine(0), "test line0");
        assertEquals(testPad.getLine(3), "test line3");
        assertThrows(InvalidIndexException.class, () -> {
            testPad.getLine(4);
        });
        assertThrows(InvalidIndexException.class, () -> {
            testPad.getLine(-1);
        });
    }

    @Test
    public void notepadViewList_AddMultipleLinesThenViewList_indexListOfNotes()
            throws FileCreationException, DataReadWriteException {
        Notepad testPad = new Notepad("notesTest6");
        populateLines(testPad);
        List<String> notes = testPad.viewList();

        for (int i = 1; i  < notes.size(); i++) {
            assertEquals(notes.get(i), String.format("%d. test line%d", i, i - 1));
        }
    }
}
