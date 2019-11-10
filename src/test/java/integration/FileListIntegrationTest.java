package integration;

import org.junit.jupiter.api.Test;
import spinbox.Parser;
import spinbox.Ui;
import spinbox.commands.Command;
import spinbox.containers.ModuleContainer;
import spinbox.containers.lists.FileList;
import spinbox.entities.Module;
import spinbox.entities.items.File;
import spinbox.exceptions.InputException;
import spinbox.exceptions.InvalidIndexException;
import spinbox.exceptions.SpinBoxException;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;


public class FileListIntegrationTest {
    private ModuleContainer testContainer;
    private Module testModule;
    private FileList fileList;
    private ArrayDeque<String> pageTrace;
    private Command command;
    private Ui ui;

    @Test
    public void setNameSuccessful_setNameOfFileToANewName_noteDescriptionSuccessfullySet() throws
            SpinBoxException {
        testContainer = new ModuleContainer();
        testModule = new Module("TESTMOD", "Test Module");
        testContainer.addModule(testModule);

        fileList = testModule.getFiles();
        while (fileList.size() > 0) {
            fileList.remove(0);
        }
        fileList.add(new File(0, "file1"));

        pageTrace = new ArrayDeque<>();
        ui = new Ui(true);

        String setNameForNote1 = "set-name TESTMOD / file 1 to: file2";
        Parser.setPageTrace(pageTrace);
        command = Parser.parse(setNameForNote1);
        command.execute(testContainer, pageTrace, ui, false);

        assertEquals(fileList.get(0).toString(), "[NOT DOWNLOADED] file2");
        testContainer.removeModule(testModule.getModuleCode(),testModule);
    }

    @Test
    public void setNameUnsuccessful_invalidIndexUsed_exceptionThrown() throws
            SpinBoxException {
        testContainer = new ModuleContainer();
        testModule = new Module("TESTMOD", "Test Module");
        testContainer.addModule(testModule);

        fileList = testModule.getFiles();
        while (fileList.size() > 0) {
            fileList.remove(0);
        }
        fileList.add(new File(0, "file1"));

        pageTrace = new ArrayDeque<>();
        ui = new Ui(true);

        try {
            String setNameForFile1 = "set-name TESTMOD / file 2 to: file2";
            Parser.setPageTrace(pageTrace);
            command = Parser.parse(setNameForFile1);
            command.execute(testContainer, pageTrace, ui, false);
            fail();
        } catch (InvalidIndexException e) {
            testContainer.removeModule(testModule.getModuleCode(),testModule);
            assertEquals("Invalid Input\n\nYou have entered an invalid index.", e.getMessage());
        }
    }

    @Test
    public void setDateUnsuccessful_setDateUsedOnNonSchedulableItems_exceptionThrown() throws
            SpinBoxException {
        testContainer = new ModuleContainer();
        testModule = new Module("TESTMOD", "Test Module");
        testContainer.addModule(testModule);

        fileList = testModule.getFiles();
        while (fileList.size() > 0) {
            fileList.remove(0);
        }
        fileList.add(new File(0, "file1"));

        pageTrace = new ArrayDeque<>();
        ui = new Ui(true);

        try {
            String setDateForFile1 = "set-date TESTMOD / file 1 to: tomorrow";
            Parser.setPageTrace(pageTrace);
            command = Parser.parse(setDateForFile1);
            command.execute(testContainer, pageTrace, ui, false);
            fail();
        } catch (InputException e) {
            testContainer.removeModule(testModule.getModuleCode(),testModule);
            assertEquals("Invalid Input\n\nSorry, set-date is not available for the selected tab.",
                    e.getMessage());
        }
    }
}
