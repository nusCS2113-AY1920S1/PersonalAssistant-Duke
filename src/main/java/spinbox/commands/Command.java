package spinbox.commands;

import spinbox.containers.ModuleContainer;
import spinbox.entities.Module;
import spinbox.exceptions.SpinBoxException;
import spinbox.Storage;
import spinbox.Ui;
import spinbox.containers.lists.TaskList;

import java.util.HashMap;
import java.util.ArrayDeque;

public abstract class Command {
    private boolean isExit;
    private boolean isFileCommand;

    // New execute needs to be made abstract
    public String execute(ModuleContainer moduleContainer, ArrayDeque<String> pageTrace, Ui ui)
            throws SpinBoxException {
        return "Blank";
    }

    // Old execute
    public String execute(TaskList taskList, Storage storage, Ui ui) throws SpinBoxException {
        return null;
    }

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
