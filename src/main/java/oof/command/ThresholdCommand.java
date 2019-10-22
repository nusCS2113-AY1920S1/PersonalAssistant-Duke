package oof.command;

import oof.Storage;
import oof.model.module.SemesterList;
import oof.model.task.TaskList;
import oof.Ui;
import oof.exception.OofException;

/**
 * Represents a command to choose the threshold for upcoming deadline reminders.
 */
public class ThresholdCommand extends Command {

    private String newThreshold;

    /**
     * Constructor for ThresholdCommand.
     *
     * @param newThreshold New threshold input by user.
     */
    public ThresholdCommand(String newThreshold) {
        super();
        this.newThreshold = newThreshold;
    }

    /**
     * Sets the threshold for upcoming deadline reminders.
     * @param semesterList Instance of SemesterList that stores Semester objects.
     * @param tasks        Instance of TaskList that stores Task objects.
     * @param ui           Instance of Ui that is responsible for visual feedback.
     * @param storage      Instance of Storage that enables the reading and writing of Task
     *                     objects to hard disk.
     * @throws OofException if threshold given is invalid.
     */
    @Override
    public void execute(SemesterList semesterList, TaskList tasks, Ui ui, Storage storage) throws OofException {
        if (!isThresholdNegative(newThreshold)) {
            updateThreshold(newThreshold, storage);
            ui.printUpdatedThreshold(newThreshold);
        } else {
            throw new OofException("Threshold given invalid! Please input positive numbers.");
        }
    }

    /**
     * Updates the threshold to the new threshold input by user.
     *
     * @param newThreshold New threshold input by user.
     */
    public void updateThreshold(String newThreshold, Storage storage) {
        storage.writeThreshold(newThreshold);
    }

    /**
     * Checks if the new threshold given by user is negative.
     *
     * @param newThreshold New threshold input by user.
     * @return  true if threshold given is negative, false otherwise.
     */
    public boolean isThresholdNegative(String newThreshold) {
        int threshold = Integer.parseInt(newThreshold);
        return threshold < 0;
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
