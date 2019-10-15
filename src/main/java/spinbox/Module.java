package spinbox;

import spinbox.lists.FileList;
import spinbox.lists.GradeList;
import spinbox.lists.TaskList;

public class Module {
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
    public Module(String moduleCode, String moduleName) {
        this.moduleCode = moduleCode;
        this.moduleName = moduleName;
        files = new FileList();
        tasks = new TaskList();
        grades = new GradeList();
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
}
