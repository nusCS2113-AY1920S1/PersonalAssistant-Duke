package spinbox.lists;

import spinbox.Module;
import spinbox.Storage;
import spinbox.exceptions.FileCreationException;
import spinbox.exceptions.StorageException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModuleContainer {
    private static final String DIRECTORY_NAME = "SpinBoxData/";
    private static final String MODULES_FILE_NAME = "modules.txt";

    private HashMap<String, Module> modules;
    private Storage localStorage;

    public ModuleContainer() throws FileCreationException {
        modules = new HashMap<>();
        localStorage = new Storage(DIRECTORY_NAME + MODULES_FILE_NAME);
    }

    public HashMap<String, Module> getModules() {
        return modules;
    }

    /**
     * Saves data using the localStorage instance to the relevant .txt file.
     * @throws StorageException I/O error.
     */
    public void saveData() throws StorageException {
        List<String> dataToSave = new ArrayList<>();
        for (Map.Entry<String, Module> entry : modules.entrySet()) {
            dataToSave.add(entry.getValue().storeString());
        }
        localStorage.saveData(dataToSave);
    }

    /**
     * Loads data using the localStorage instance from the relevant .txt file.
     * @throws StorageException I/O error.
     */
    public void loadData() throws StorageException {
        List<String> savedData = localStorage.loadData();
        for (String datum : savedData) {
            Module temp = new Module(datum);
            this.modules.put(temp.getModuleCode(), temp);
        }
    }
}
