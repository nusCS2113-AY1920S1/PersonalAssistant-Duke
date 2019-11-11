package executor.command;

import storage.task.TaskList;
import storage.StorageManager;
import ui.UiCode;
import utils.InfoCapsule;

import java.util.ArrayList;
import java.util.List;

public abstract class Command {
    protected InfoCapsule infoCapsule;
    private static List<String> executedCommands = new ArrayList<>();
    protected Boolean exitRequest = false;
    protected String userInput = null;
    protected CommandType commandType;
    protected String description = "NO DESCRIPTION";
    protected TaskList taskList;

    /**
     * Base Constructor for all sub-classes to call super().
     */
    public Command() {
        this.infoCapsule = new InfoCapsule();
        infoCapsule.setUiCode(UiCode.ERROR);
        infoCapsule.setOutputStr("Command was not executed.\n");
    }

    /**
     * Returns an InfoCapsule that details the Execution Info/Status of this Command.
     * @return Boolean
     */
    public InfoCapsule getInfoCapsule() {
        return this.infoCapsule;
    }

    /**
     * Executes a particular Command.
     * @param storageManager StorageManager Object that holds all the Models of Duke
     */
    public abstract void execute(StorageManager storageManager);

    public String getDescription() {
        return this.description;
    }

    public CommandType getCommandType() {
        return commandType;
    }

    public static List<String> getExecutedCommands() {
        return executedCommands;
    }
}
