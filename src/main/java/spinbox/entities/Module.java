package spinbox.entities;

import spinbox.containers.Notepad;
import spinbox.exceptions.CorruptedDataException;
import spinbox.exceptions.DataReadWriteException;
import spinbox.exceptions.DateFormatException;
import spinbox.exceptions.FileCreationException;
import spinbox.containers.lists.FileList;
import spinbox.containers.lists.GradeList;
import spinbox.containers.lists.TaskList;
import spinbox.datapersistors.storage.Storable;

public class Module implements Storable {
    private static final String STORE_DELIMITER = " | ";
    private static final String DELIMITER_FILTER = " \\| ";

    private String moduleCode;
    private String moduleName;
    private FileList files;
    private TaskList tasks;
    private GradeList grades;
    private Notepad notepad;

    /**
     * Constructor for module.
     */
    public Module(String moduleCode, String moduleName) throws FileCreationException,
            DataReadWriteException, CorruptedDataException, DateFormatException {
        this.moduleCode = moduleCode;
        this.moduleName = moduleName;
        this.files = new FileList(moduleCode);
        this.tasks = new TaskList(moduleCode);
        this.grades = new GradeList(moduleCode);
        this.notepad = new Notepad(moduleCode);
        this.loadData();
    }

    /**
     * Empty constructor to be used for retrieval and re-population from storage.
     */
    public Module() {

    }

    /**
     * Returns a human readable String of module information.
     * @return String containing code and name.
     */
    @Override
    public String toString() {
        return this.getModuleCode().concat(" ").concat(this.getModuleName());
    }

    /**
     * Prepares the module into a stringified version for storage.
     * @return String containing a formatted string for storage.
     */
    public String storeString() {
        return this.getModuleCode() + STORE_DELIMITER + this.getModuleName();
    }

    /**
     * Parses a string extracted from storage back into a Module object.
     * @param fromStorage This String is provided directly from the localStorage instance.
     * @throws CorruptedDataException Thrown when a user manually edits the .txt file incorrectly such that
     *     the data cannot be extracted or such a file is not located where expected, or is not available for RW.
     */
    @Override
    public void fromStoredString(String fromStorage) throws CorruptedDataException {
        try {
            String[] components = fromStorage.split(DELIMITER_FILTER);
            this.setModuleCode(components[0]);
            this.setModuleName(components[1]);
            this.files = new FileList(moduleCode);
            this.tasks = new TaskList(moduleCode);
            this.grades = new GradeList(moduleCode);
            this.notepad = new Notepad(moduleCode);
            this.loadData();
        } catch (ArrayIndexOutOfBoundsException | FileCreationException
                | DataReadWriteException | DateFormatException e) {
            throw new CorruptedDataException();
        }
    }

    /**
     * Get module name.
     * @return module name.
     */
    public String getModuleName() {
        return this.moduleName;
    }

    /**
     * Get module code.
     * @return module code.
     */
    public String getModuleCode() {
        return this.moduleCode;
    }

    /**
     * Get the FileList.
     * @return FileList instance.
     */
    public FileList getFiles() {
        return files;
    }

    /**
     * Get the GradeList.
     * @return GradeList instance.
     */
    public GradeList getGrades() {
        return grades;
    }

    /**
     * Get the TaskList.
     * @return TaskList instance.
     */
    public TaskList getTasks() {
        return tasks;
    }

    /**
     * Gets the notepad.
     * @return Notepad instance.
     */
    public Notepad getNotepad() {
        return notepad;
    }

    /**
     * To be used upon once Module object has been constructed to populate pre-existing data.
     * @throws DataReadWriteException I/O error.
     * @throws CorruptedDataException Data has been modified incorrectly within the .txt files.
     */
    public void loadData() throws DataReadWriteException, CorruptedDataException, DateFormatException {
        this.files.loadData();
        this.tasks.loadData();
        this.grades.loadData();
        this.notepad.loadData();
    }

    private void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    private void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }
}
