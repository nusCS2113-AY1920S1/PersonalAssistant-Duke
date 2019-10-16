package spinbox.containers;

import spinbox.entities.Module;
import spinbox.Storage;
import spinbox.exceptions.CorruptedDataException;
import spinbox.exceptions.DataReadWriteException;
import spinbox.exceptions.FileCreationException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModuleContainer {
    private static final String DIRECTORY_NAME = "SpinBoxData/";
    private static final String MODULES_FILE_NAME = "modules.txt";

    private HashMap<String, Module> modules;
    private Storage localStorage;

    /**
     * Constructor for a module container. Retrieves added modules, populates them and stores in program memory.
     * @throws FileCreationException Creation of file hierarchy failed, perhaps due to permissions.
     * @throws DataReadWriteException I/O error during file read/writes.
     * @throws CorruptedDataException Text files have been improperly modified (unexpected formatting).
     */
    public ModuleContainer() throws FileCreationException, DataReadWriteException, CorruptedDataException {
        modules = new HashMap<>();
        localStorage = new Storage(DIRECTORY_NAME + MODULES_FILE_NAME);
        this.loadData();
    }

    public HashMap<String, Module> getModules() {
        return modules;
    }

    /**
     * Saves data using the localStorage instance to the relevant .txt file.
     * @throws DataReadWriteException I/O error.
     */
    public void saveData() throws DataReadWriteException {
        List<String> dataToSave = new ArrayList<>();
        for (Map.Entry<String, Module> entry : modules.entrySet()) {
            dataToSave.add(entry.getValue().storeString());
        }
        localStorage.saveData(dataToSave);
    }

    /**
     * Adds a module to be saved to the module container.
     * @param module Module object to be added.
     * @return the Module object which has been stored.
     * @throws DataReadWriteException I/O Error.
     */
    public Module addModule(Module module) throws DataReadWriteException {
        this.getModules().put(module.getModuleCode(), module);
        this.saveData();
        return module;
    }

    /**
     * Check the existence of a module within SpinBox.
     * @param moduleCode A String denoting the module code.
     * @return True if the module already exists.
     */
    public boolean checkModuleExists(String moduleCode) {
        return this.getModules().containsKey(moduleCode);
    }

    /**
     * Loads data using the localStorage instance from the relevant .txt file.
     * @throws DataReadWriteException I/O error.
     * @throws CorruptedDataException polluted data within txt files.
     */
    public void loadData() throws DataReadWriteException, CorruptedDataException, FileCreationException {
        List<String> savedData = localStorage.loadData();
        for (String datum : savedData) {
            Module temp = new Module(datum);
            temp.loadData();
            this.modules.put(temp.getModuleCode(), temp);
        }
    }
}
