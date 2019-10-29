package oof.command;

import oof.Storage;
import oof.model.module.SemesterList;
import oof.Ui;
import oof.model.task.TaskList;

/**
 * Represents a Command to list all Tasks in the TaskList.
 */
public class ListCommand extends Command {

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
     * @param storage      Instance of Storage that enables the reading and writing of Task
     */
    public void execute(SemesterList semesterList, TaskList taskList, Ui ui, Storage storage) {
        ui.printTaskList(taskList);
    }
}
