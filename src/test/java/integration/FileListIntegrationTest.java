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
    @Test
    void testFileList_addingFiles() throws SpinBoxException {
        FileList fileList = new FileList("testFileList");
        fileList.add(new File(0, "file1"));
        fileList.add(new File(0, "file2"));
        fileList.add(new File(0, "file3"));

        List<File> checkList = new ArrayList<>();
        checkList.add(new File(0, "file1"));
        checkList.add(new File(0, "file2"));
        checkList.add(new File(0, "file3"));

        assertEquals(checkList.toString(), fileList.getList().toString());
    }

    @Test
    void testFileList_removingFiles() throws SpinBoxException {
        FileList fileList = new FileList("testFileList");
        fileList.add(new File(0, "file1"));
        fileList.add(new File(0, "file2"));
        fileList.add(new File(0, "file3"));

        List<File> checkList = new ArrayList<>();
        checkList.add(new File(0, "file1"));
        checkList.add(new File(0, "file2"));
        checkList.add(new File(0, "file3"));

        fileList.remove(1);
        checkList.remove(1);

        assertEquals(checkList.toString(), fileList.getList().toString());
    }

    @Test
    void testFileList_getFile() throws SpinBoxException {
        FileList fileList = new FileList("testFileList");
        fileList.add(new File(0, "file1"));
        fileList.add(new File(0, "file2"));
        fileList.add(new File(0, "file3"));

        File file = fileList.get(1);

        assertEquals("[NOT DOWNLOADED] file2", file.toString());
    }

    @Test
    void testFileList_update() throws SpinBoxException {
        FileList fileList = new FileList("testFileList");
        fileList.add(new File(0, "file1"));
        fileList.add(new File(0, "file2"));
        fileList.add(new File(0, "file3"));

        fileList.update(1, true);
        File file = fileList.get(1);

        assertEquals("[DOWNLOADED] file2", file.toString());
    }

    @Test
    void testFileList_contains() throws SpinBoxException {
        FileList fileList = new FileList("testFileList");
        fileList.add(new File(0, "a"));
        fileList.add(new File(0, "b"));
        fileList.add(new File(0, "c"));

        List<String> containsList = fileList.containsKeyword("a");
        List<String> checkList = new ArrayList<>();
        checkList.add("Here are the files that contain a in your module:");
        checkList.add("1. [NOT DOWNLOADED] a");

        assertTrue(checkList.equals(containsList));
    }

    @Test
    void testFileList_sort() throws SpinBoxException {
        FileList fileList = new FileList("testFileList");
        fileList.add(new File(0, "file3"));
        fileList.add(new File(0, "file1"));
        fileList.add(new File(0, "file2"));

        List<File> checkList = new ArrayList<>();
        checkList.add(new File(0, "file1"));
        checkList.add(new File(0, "file2"));
        checkList.add(new File(0, "file3"));

        assertEquals(checkList.toString(), fileList.getList().toString());
    }

    @Test
    public void setNameSuccessful_setNameOfFileToANewName_noteDescriptionSuccessfullySet() throws
            SpinBoxException {
        ModuleContainer testContainer = new ModuleContainer();
        Module testModule = new Module("CG1112", "Engineering Principles & Practice III");
        testContainer.addModule(testModule);

        FileList fileList = testModule.getFiles();
        while (fileList.size() > 0) {
            fileList.remove(0);
        }
        fileList.add(new File(0, "file1"));

        ArrayDeque<String> pageTrace = new ArrayDeque<>();
        Ui ui = new Ui(true);

        String setNameForNote1 = "set-name CG1112 / file 1 to: file2";
        Parser.setPageTrace(pageTrace);
        Command command = Parser.parse(setNameForNote1);
        command.execute(testContainer, pageTrace, ui, false);

        assertEquals(fileList.get(0).toString(), "[NOT DOWNLOADED] file2");
    }

    @Test
    public void setNameUnsuccessful_invalidIndexUsed_exceptionThrown() throws
            SpinBoxException {
        ModuleContainer testContainer = new ModuleContainer();
        Module testModule = new Module("CG1112", "Engineering Principles & Practice III");
        testContainer.addModule(testModule);

        FileList fileList = testModule.getFiles();
        while (fileList.size() > 0) {
            fileList.remove(0);
        }
        fileList.add(new File(0, "file1"));

        ArrayDeque<String> pageTrace = new ArrayDeque<>();
        Ui ui = new Ui(true);

        try {
            String setNameForFile1 = "set-name CG1112 / file 2 to: file2";
            Parser.setPageTrace(pageTrace);
            Command command = Parser.parse(setNameForFile1);
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

        FileList fileList = testModule.getFiles();
        while (fileList.size() > 0) {
            fileList.remove(0);
        }
        fileList.add(new File(0, "file1"));

        ArrayDeque<String> pageTrace = new ArrayDeque<>();
        Ui ui = new Ui(true);

        try {
            String setDateForFile1 = "set-date CG1112 / file 1 to: tomorrow";
            Parser.setPageTrace(pageTrace);
            Command command = Parser.parse(setDateForFile1);
            command.execute(testContainer, pageTrace, ui, false);
            fail();
        } catch (InputException e) {
            assertEquals("Invalid Input\n\nSorry, set-date is not available for the selected tab.",
                    e.getMessage());
        }
    }
}
