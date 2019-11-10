package spinbox.containers;

import spinbox.entities.Module;
import spinbox.datapersistors.storage.Storage;
import spinbox.exceptions.CorruptedDataException;
import spinbox.exceptions.DataReadWriteException;
import spinbox.exceptions.FileCreationException;
import spinbox.datapersistors.storage.StorageContainer;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModuleContainer implements StorageContainer {
    private static final String DIRECTORY_NAME = "SpinBoxData/";
    private static final String MODULES_FILE_NAME = "modules.txt";
    private static final String FILES_DIRECTORY = "/files.txt";
    private static final String GRADES_DIRECTORY = "/grades.txt";
    private static final String TASKS_DIRECTORY = "/tasks.txt";
    private static final String NOTES_DIRECTORY = "/notes.txt";

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

    /**
     * Method call returns the modules contained within this moduleContainer instance.
     * @return HashMap of String : Module of modules.
     */
    public HashMap<String, Module> getModules() {
        assert modules != null;
        return modules;
    }

    /**
     * Saves data using the localStorage instance to the relevant .txt file.
     * @throws DataReadWriteException I/O error.
     */
    @Override
    public void saveData() throws DataReadWriteException {
        List<String> dataToSave = new ArrayList<>();
        for (Map.Entry<String, Module> entry : modules.entrySet()) {
            dataToSave.add(entry.getValue().storeString());
        }
        localStorage.writeData(dataToSave);
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
     * Removes a module from the module container.
     * @param moduleCode ModuleCode of the module to be removed.
     * @param module Module object to be removed.
     * @throws DataReadWriteException I/O Error.
     */
    public void removeModule(String moduleCode, Module module) throws DataReadWriteException {
        this.getModules().remove(module.getModuleCode());
        File file1 = new File(DIRECTORY_NAME + moduleCode + FILES_DIRECTORY);
        file1.delete();
        File file2 = new File(DIRECTORY_NAME + moduleCode + GRADES_DIRECTORY);
        file2.delete();
        File file3 = new File(DIRECTORY_NAME + moduleCode + NOTES_DIRECTORY);
        file3.delete();
        File file4 = new File(DIRECTORY_NAME + moduleCode + TASKS_DIRECTORY);
        file4.delete();
        File file5 = new File(DIRECTORY_NAME + moduleCode);
        file5.delete();
        this.saveData();
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
     * Gets a specific module from within the container.
     * @param moduleCode A string containing the module code to be used as the key.
     * @return a Module object, or null if no such module exists.
     */
    public Module getModule(String moduleCode) {
        if (this.checkModuleExists(moduleCode)) {
            return this.getModules().get(moduleCode);
        } else {
            return null;
        }
    }

    /**
     * Loads data using the localStorage instance from the relevant .txt file.
     * @throws DataReadWriteException I/O error.
     * @throws CorruptedDataException polluted data within txt files.
     */
    @Override
    public void loadData() throws DataReadWriteException, CorruptedDataException {
        List<String> savedData = localStorage.loadData();
        for (String datum : savedData) {
            Module module = new Module();
            module.fromStoredString(datum);
            this.modules.put(module.getModuleCode(), module);
        }
    }
}
