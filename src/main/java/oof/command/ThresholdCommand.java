package oof.command;

import oof.Storage;
import oof.TaskList;
import oof.Ui;
import oof.exception.OofException;

/**
 * Represents a command to choose the threshold for upcoming deadlines.
 */
public class ThresholdCommand extends Command {

    private String newThreshold;
    private Storage storage = new Storage();

    /**
     * Constructor for ThresholdCommand.
     *
     * @param newThreshold New threshold input by user.
     */
    public ThresholdCommand (String newThreshold) {
        super();
        this.newThreshold = newThreshold;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        updateThreshold(newThreshold);
        ui.printUpdatedThreshold(newThreshold);
    }

    /**
     * Updates the threshold to the new threshold input by user.
     *
     * @param newThreshold New threshold input by user.
     * @throws OofException Throws an exception if file to be updated cannot be found.
     */
    public void updateThreshold (String newThreshold) {
        storage.writeThreshold(newThreshold);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
