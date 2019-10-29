package oof.command;

import oof.Storage;
import oof.model.module.SemesterList;
import oof.Ui;
import oof.model.task.TaskList;

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
     * @param semesterList Instance of SemesterList that stores Semester objects.
     * @param taskList     Instance of TaskList that stores Task objects.
     * @param ui           Instance of Ui that is responsible for visual feedback.
     * @param storage      Instance of Storage that enables the reading and writing of Task
     */
    public void execute(SemesterList semesterList, TaskList taskList, Ui ui, Storage storage) {
        ui.printByeMessage();
    }

    /**
     * Checks if ExitCommand is called for Oof to terminate.
     *
     * @return true.
     */
    @Override
    public boolean isExit() {
        return true;
    }

}