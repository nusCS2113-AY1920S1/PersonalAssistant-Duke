package integration;

import org.junit.jupiter.api.Test;
import spinbox.containers.ModuleContainer;
import spinbox.containers.Notepad;
import spinbox.exceptions.CorruptedDataException;
import spinbox.exceptions.DataReadWriteException;
import spinbox.exceptions.FileCreationException;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
