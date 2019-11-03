package oof.command;

import oof.model.module.SemesterList;
import oof.Ui;
import oof.model.task.TaskList;
import oof.storage.StorageManager;

/**
 * Represents a Command to list all Tasks in the TaskList.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    /**
     * Constructor for ListCommand.
     */
    public ListCommand() {
        super();
    }

    /**
     * List all the Tasks present in the TaskList.
     * @param semesterList Instance of SemesterList that stores Semester objects.
     * @param taskList     Instance of TaskList that stores Task objects.
     * @param ui           Instance of Ui that is responsible for visual feedback.
     * @param storageManager      Instance of Storage that enables the reading and writing of Task
     */
    public void execute(SemesterList semesterList, TaskList taskList, Ui ui, StorageManager storageManager) {
        ui.printTaskList(taskList);
    }
}
