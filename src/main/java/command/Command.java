package command;

import exception.DukeException;
import storage.Storage;
import tasklist.TaskList;
import ui.Ui;

/**
 * Abstract class to represent command
 */
public abstract class Command {
    protected String userInputCommand;

    public abstract void execute(TaskList taskList, Ui ui, Storage storage) throws DukeException;
    public abstract boolean isExit();
}
