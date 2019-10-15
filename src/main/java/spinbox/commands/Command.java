package spinbox.commands;

import spinbox.exceptions.SpinBoxException;
import spinbox.Storage;
import spinbox.Ui;
import spinbox.lists.TaskList;

import java.util.Stack;

public abstract class Command {
    private boolean isExit;
    private boolean isFileCommand;

    public abstract String execute(TaskList taskList, Storage storage, Ui ui) throws SpinBoxException;

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
