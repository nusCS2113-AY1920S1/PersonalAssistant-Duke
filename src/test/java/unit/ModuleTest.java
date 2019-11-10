package unit;

import org.junit.jupiter.api.Test;
import spinbox.entities.Module;
import spinbox.exceptions.CorruptedDataException;
import spinbox.exceptions.DataReadWriteException;
import spinbox.exceptions.FileCreationException;
import spinbox.exceptions.StorageException;
import spinbox.exceptions.DateFormatException;
import spinbox.containers.ModuleContainer;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ModuleTest {

    @Test
    public void moduleCreation_variousModules_successfulCreationWithWorkingGetters() throws FileCreationException,
            CorruptedDataException, DataReadWriteException, DateFormatException {
        ModuleContainer testContainer = new ModuleContainer();
        Module testModuleOne = new Module("testMod1", "Engineering Principles & Practice I");

        assertEquals(testModuleOne.getModuleCode(), "testMod1");
        assertEquals(testModuleOne.getModuleName(), "Engineering Principles & Practice I");
        assertEquals(testModuleOne.storeString(), "testMod1 | Engineering Principles & Practice I");

        Module testModuleTwo = new Module("testMod2", "Discrete Structures");

        assertEquals(testModuleTwo.getModuleCode(), "testMod2");
        assertEquals(testModuleTwo.getModuleName(), "Discrete Structures");
        assertEquals(testModuleTwo.storeString(), "testMod2 | Discrete Structures");

        Module testModuleThree = new Module("testMod3", "Engineering Principles & Practice II");

        assertEquals(testModuleThree.getModuleCode(), "testMod3");
        assertEquals(testModuleThree.getModuleName(), "Engineering Principles & Practice II");
        assertEquals(testModuleThree.storeString(), "testMod3 | Engineering Principles & Practice II");
    }

    @Test
    public void storageStringRecreation_oneModule_expectedRecreatedObject()
            throws StorageException, DateFormatException {
        ModuleContainer testContainer = new ModuleContainer();
        Module testModuleOne = new Module("testMod4", "Engineering Principles & Practice I");

        assertEquals(testModuleOne.getModuleCode(), "testMod4");
        assertEquals(testModuleOne.getModuleName(), "Engineering Principles & Practice I");

        Module testModuleOneRecreated = new Module();
        testModuleOneRecreated.fromStoredString(testModuleOne.storeString());
        assertEquals(testModuleOneRecreated.getModuleCode(), "testMod4");
        assertEquals(testModuleOneRecreated.getModuleName(), "Engineering Principles & Practice I");
        assertEquals(testModuleOneRecreated.storeString(), "testMod4 | Engineering Principles & Practice I");
    }
}
