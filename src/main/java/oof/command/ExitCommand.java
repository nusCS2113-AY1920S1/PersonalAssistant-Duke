package oof.command;

import oof.Storage;
import oof.model.module.SemesterList;
import oof.model.task.TaskList;
import oof.Ui;

/**
 * Represents a Command to terminate Oof.
 */
public class ExitCommand extends Command {

    /**
     * Constructor for ExitCommand.
     */
    public ExitCommand() {
        super();
    }

    /**
     * Exits the Oof program.
     *
     * @param semesterList Instance of SemesterList that stores Semester objects.
     * @param tasks        Instance of TaskList that stores Task objects.
     * @param ui           Instance of Ui that is responsible for visual feedback.
     * @param storage      Instance of Storage that enables the reading and writing of Task
     */
    public void execute(SemesterList semesterList, TaskList tasks, Ui ui, Storage storage) {
        ui.printByeMessage();
    }

    /**
     * Checks if ExitCommand is called for Oof to terminate.
     *
     * @return true.
     */
    public boolean isExit() {
        return true;
    }

}