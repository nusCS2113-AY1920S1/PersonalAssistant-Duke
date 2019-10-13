package duke;

import duke.lists.FileList;
import duke.lists.GradeList;
import duke.lists.TaskList;

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
