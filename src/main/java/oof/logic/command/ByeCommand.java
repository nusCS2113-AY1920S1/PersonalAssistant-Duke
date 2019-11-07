package oof.logic.command;

import oof.model.semester.SemesterList;
import oof.ui.Ui;
import oof.model.task.TaskList;
import oof.storage.StorageManager;

/**
 * Represents a Command to terminate Oof.
 */
public class ByeCommand extends Command {

    public static final String COMMAND_WORD = "bye";

    /**
     * Constructor for ExitCommand.
     */
    public ByeCommand() {
        super();
    }

    /**
     * Exits the Oof program.
     *
     * @param semesterList   Instance of SemesterList that stores Semester objects.
     * @param taskList       Instance of TaskList that stores Task objects.
     * @param ui             Instance of Ui that is responsible for visual feedback.
     * @param storageManager Instance of Storage that enables the reading and writing of Task
     */
    public void execute(SemesterList semesterList, TaskList taskList, Ui ui, StorageManager storageManager) {
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