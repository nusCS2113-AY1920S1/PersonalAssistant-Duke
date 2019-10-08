package oof.command;

import oof.Storage;
import oof.TaskList;
import oof.Ui;
import oof.exception.OofException;

import java.io.IOException;

public class HelpCommand extends Command {

    /**
     * Invokes other Command subclasses based on the input given by the user.
     *
     * @param tasks   Instance of TaskList that stores Task objects.
     * @param ui      Instance of Ui that is responsible for visual feedback.
     * @param storage Instance of Storage that enables the reading and writing of Task
     *                objects to hard disk.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws OofException {
        ui.printHelpCommands();
    }

    /**
     * Checks if ExitCommand is called for Oof to terminate.
     *
     * @return true if ExitCommand is called, false otherwise.
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
