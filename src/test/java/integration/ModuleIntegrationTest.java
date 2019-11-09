package integration;

import org.junit.jupiter.api.Test;
import spinbox.Parser;
import spinbox.Ui;
import spinbox.commands.Command;
import spinbox.containers.ModuleContainer;
import spinbox.entities.Module;
import spinbox.entities.items.File;
import spinbox.entities.items.GradedComponent;
import spinbox.entities.items.tasks.Todo;
import spinbox.exceptions.SpinBoxException;
import spinbox.exceptions.StorageException;
import spinbox.exceptions.InputException;

import java.util.ArrayDeque;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;

public class ModuleIntegrationTest {
    private ModuleContainer testContainer;
    private Module testModule;
    private ArrayDeque<String> pageTrace;
    private Command command;
    private Ui ui;

    @Test
    public void loadDataFromStorageSuccessful_oneModule_expectedFilesLoaded() throws StorageException {
        Module testModuleOne = new Module("testMod5", "Engineering Principles & Practice III");

        testModuleOne.getFiles().add(new File(0, "testFile1"));
        testModuleOne.getGrades().add(new GradedComponent("Essay", 20));
        testModuleOne.getTasks().add(new Todo("test todo"));
        testModuleOne.getNotepad().addLine("hello123");

        testModuleOne.getFiles().getList().clear();
        testModuleOne.getGrades().getList().clear();
        testModuleOne.getTasks().getList().clear();
        testModuleOne.getNotepad().getNotes().clear();

        testModuleOne.loadData();

        assertEquals(testModuleOne.getFiles().getList().remove(0).storeString(),
                new File(0, "testFile1").storeString());

        assertEquals(testModuleOne.getGrades().getList().remove(0).storeString(),
                new GradedComponent("Essay", 20).storeString());

        assertEquals(testModuleOne.getTasks().getList().remove(0).storeString(),
                new Todo("test todo").storeString());

        assertEquals(testModuleOne.getNotepad().getNotes().remove(0), "hello123");
    }

    @Test
    public void moduleRemoval_removeOneModule_expectedModuleRemoved() throws SpinBoxException {
        testContainer = new ModuleContainer();
        String moduleCode = "testMod";
        String moduleName = "test";
        testModule = new Module(moduleCode, moduleName);

        testContainer.addModule(testModule);
        assertTrue(testContainer.checkModuleExists(moduleCode));

        testContainer.removeModule(moduleCode, testModule);
        assertFalse(testContainer.checkModuleExists(moduleCode));
    }

    @Test
    public void moduleRemoval_removeOneModuleThroughCommand_expectedModuleRemoved() throws SpinBoxException {
        testContainer = new ModuleContainer();
        pageTrace = new ArrayDeque<>();
        pageTrace.add("main");
        ui = new Ui(true);

        String addOneModule = "add / module TESTMOD Test Module";
        Parser.setPageTrace(pageTrace);
        command = Parser.parse(addOneModule);
        command.execute(testContainer, pageTrace, ui, false);
        assertTrue(testContainer.checkModuleExists("TESTMOD"));

        String removeOneModule = "remove / module TESTMOD";
        Parser.setPageTrace(pageTrace);
        command = Parser.parse(removeOneModule);
        command.execute(testContainer, pageTrace, ui, false);
        assertFalse(testContainer.checkModuleExists("TESTMOD"));
    }

    @Test
    public void unsuccessfulModuleRemoval_provideNonExistentModule_exceptionThrown()
            throws SpinBoxException {
        testContainer = new ModuleContainer();
        testModule = new Module("TESTMOD", "Test Module");
        testContainer.addModule(testModule);

        pageTrace = new ArrayDeque<>();
        pageTrace.add("main");
        ui = new Ui(true);

        try {
            String removeOneModule = "remove / module random";
            Parser.setPageTrace(pageTrace);
            command = Parser.parse(removeOneModule);
            command.execute(testContainer, pageTrace, ui, false);
            fail();
        } catch (InputException e) {
            testContainer.removeModule(testModule.getModuleCode(),testModule);
            assertEquals("Invalid Input\n\nThis module does not exist.",
                    e.getMessage());
        }
    }

    @Test
    public void unsuccessfulModuleRemoval_provideInvalidCommand_exceptionThrown()
            throws SpinBoxException {
        testContainer = new ModuleContainer();
        testModule = new Module("TESTMOD", "Test Module");
        testContainer.addModule(testModule);

        pageTrace = new ArrayDeque<>();
        pageTrace.add("main");
        ui = new Ui(true);

        try {
            String removeOneModule = "remove module / TESTMOD";
            Parser.setPageTrace(pageTrace);
            command = Parser.parse(removeOneModule);
            command.execute(testContainer, pageTrace, ui, false);
            fail();
        } catch (InputException e) {
            testContainer.removeModule(testModule.getModuleCode(),testModule);
            assertEquals("Invalid Input\n\nPlease use valid remove format:\n"
                            + "\t1. To remove a module: remove / module <moduleCode>\n"
                            + "\t2. To remove an item from a module component: remove <pageContent> / <type> <index>",
                    e.getMessage());
        }
    }
}
