package integration;

import org.junit.jupiter.api.Test;
import spinbox.containers.ModuleContainer;
import spinbox.entities.Module;
import spinbox.entities.items.File;
import spinbox.entities.items.GradedComponent;
import spinbox.entities.items.tasks.Todo;
import spinbox.exceptions.SpinBoxException;
import spinbox.exceptions.StorageException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ModuleIntegrationTest {
    @Test
    public void loadDataFromStorageSuccessful_oneModule_expectedFilesLoaded() throws StorageException {
        ModuleContainer testContainer = new ModuleContainer();
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
        ModuleContainer testContainer = new ModuleContainer();
        String moduleCode = "testModule";
        String moduleName = "test";
        Module testModuleOne = new Module(moduleCode, moduleName);

        testContainer.addModule(testModuleOne);
        assertTrue(testContainer.checkModuleExists(moduleCode));

        testContainer.removeModule(moduleCode, testModuleOne);
        assertFalse(testContainer.checkModuleExists(moduleCode));
    }
}
