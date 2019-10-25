package spinbox.commands;

import spinbox.containers.ModuleContainer;
import spinbox.exceptions.InputException;
import spinbox.exceptions.SpinBoxException;
import spinbox.storage.Storage;
import spinbox.Ui;
import spinbox.containers.lists.TaskList;

import java.util.ArrayDeque;

public abstract class Command {
    private static final String NOT_ON_MODULE_PAGE = "Please be on a module page or indicate "
            + "the specific module this action is for.";
    private boolean isExit;
    private boolean isFileCommand;

    // New execute needs to be made abstract
    public String execute(ModuleContainer moduleContainer, ArrayDeque<String> pageTrace, Ui ui, boolean guiMode)
            throws SpinBoxException {
        return "Blank";
    }

    // Old execute
    public String execute(TaskList taskList, Storage storage, Ui ui, boolean guiMode) throws SpinBoxException {
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

    /**
     * Check when there is a moduleCode indicated from the constructor.
     * @param moduleCode the moduleCode variable.
     * @return true if there is a moduleCode indicated, false otherwise.
     * @throws InputException if there is no moduleCode indicated, tell User.
     */
    public boolean checkIfOnModulePage(String moduleCode) throws InputException {
        if (moduleCode == null) {
            throw new InputException(NOT_ON_MODULE_PAGE);
        }
        return true;
    }
}
