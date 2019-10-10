package duke.commands;

import duke.exceptions.DukeException;
import duke.Storage;
import duke.TaskList;
import duke.Ui;

public abstract class Command {
    private boolean isExit;
    private boolean isFileCommand;

    public abstract String execute(TaskList taskList, Storage storage, Ui ui) throws DukeException;

    public boolean isExit() {
        return isExit;
    }

    /**
     * Check whether it is file command.
     * @return whether it is file command.
     */
    public boolean isFileCommand() {
        return isFileCommand;
    }

    public void setExit(boolean exit) {
        isExit = exit;
    }

    /**
     * Set the isFileCommand value.
     * @param fileCommand the value of isFileCommand.
     */
    public void setFileCommand(boolean fileCommand) {
        isFileCommand = fileCommand;
    }
}
