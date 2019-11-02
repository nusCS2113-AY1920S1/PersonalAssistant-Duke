package oof.command;

import oof.model.module.SemesterList;
import oof.Ui;
import oof.exception.OofException;
import oof.model.task.TaskList;
import oof.storage.StorageManager;

/**
 * Represents a command to choose the threshold for upcoming deadline reminders.
 */
public class ThresholdCommand extends Command {

    public static final String COMMAND_WORD = "threshold";
    private int newThreshold;

    /**
     * Constructor for ThresholdCommand.
     *
     * @param newThreshold New threshold input by user.
     */
    public ThresholdCommand(int newThreshold) {
        super();
        this.newThreshold = newThreshold;
    }

    /**
     * Sets the threshold for upcoming deadline reminders.
     *
     * @param semesterList   Instance of SemesterList that stores Semester objects.
     * @param tasks          Instance of TaskList that stores Task objects.
     * @param ui             Instance of Ui that is responsible for visual feedback.
     * @param storageManager Instance of Storage that enables the reading and writing of Task
     *                       objects to hard disk.
     * @throws OofException if threshold given is invalid.
     */
    @Override
    public void execute(SemesterList semesterList, TaskList tasks, Ui ui, StorageManager storageManager)
            throws OofException {
        if (isNegative(newThreshold)) {
            throw new OofException("Threshold given invalid! Please input positive integers.");
        } else {
            storageManager.writeThreshold(newThreshold);
            ui.printUpdatedThreshold(newThreshold);
        }
    }

    /**
     * Checks if the new threshold given by user is negative.
     *
     * @param threshold New value of threshold input by user.
     * @return true if threshold given is negative, false otherwise.
     */
    public boolean isNegative(int threshold) {
        return threshold < 0;
    }
}
