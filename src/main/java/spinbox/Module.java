package spinbox;

import spinbox.lists.FileList;
import spinbox.lists.GradeList;
import spinbox.lists.TaskList;

public class Module {
    private FileList files;
    private TaskList tasks;
    private GradeList grades;
    // private Storage storage;
    // private Event exam;

    /**
     * Constructor for module.
     */
    public Module() {
        files = new FileList();
        tasks = new TaskList();
        grades = new GradeList();
    }
}
