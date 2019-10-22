package oof.command;

import oof.Storage;
import oof.model.module.SemesterList;
import oof.model.task.TaskList;
import oof.Ui;

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
     * @param tasks        Instance of TaskList that stores Task objects.
     * @param ui           Instance of Ui that is responsible for visual feedback.
     * @param storage      Instance of Storage that enables the reading and writing of Task
     *                     objects to hard disk.
     */
    public void execute(SemesterList semesterList, TaskList tasks, Ui ui, Storage storage) {
        ui.printTaskList(tasks);
    }

    /**
     * Checks if ExitCommand is called for Oof to terminate.
     *
     * @return false.
     */
    public boolean isExit() {
        return false;
    }
}
