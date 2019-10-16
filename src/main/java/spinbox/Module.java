package spinbox;

import spinbox.exceptions.CorruptedDataException;
import spinbox.exceptions.FileCreationException;
import spinbox.lists.FileList;
import spinbox.lists.GradeList;
import spinbox.lists.TaskList;

public class Module {
    private static final String CORRUPTED_MODULES_DATA = "Corrupted modules data.";
    private static final String STORE_DELIMITER = " | ";
    private static final String DELIMITER_FILTER = " \\| ";

    private String moduleCode;
    private String moduleName;
    private FileList files;
    private TaskList tasks;
    private GradeList grades;
    // private Storage storage;
    // private Event exam;

    /**
     * Constructor for module.
     */
    public Module(String moduleCode, String moduleName) throws FileCreationException {
        this.moduleCode = moduleCode;
        this.moduleName = moduleName;
        this.files = new FileList(moduleCode);
        this.tasks = new TaskList(moduleCode);
        this.grades = new GradeList(moduleCode);
    }

    /**
     * Parses a string extracted from storage back into a Module object.
     * @param fromStorage This String is provided directly from the localStorage instance.
     * @throws CorruptedDataException Thrown when a user manually edits the .txt file incorrectly.
     */
    public Module(String fromStorage) throws CorruptedDataException {
        try {
            String[] components = fromStorage.split(DELIMITER_FILTER);
            this.setModuleCode(components[0]);
            this.setModuleName(components[1]);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new CorruptedDataException(CORRUPTED_MODULES_DATA);
        }
    }

    public String storeString() {
        return this.getModuleCode() + STORE_DELIMITER + this.getModuleName();
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

    private void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    private void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }
}
