package rims.command;

import rims.core.ResourceList;
import rims.core.Storage;
import rims.core.Ui;

/**
 * The parent of all possible commands understood by Duke. Sets the exit code
 * to false by default and lists methods common to all the commands.
 */

public abstract class Command {
    protected Boolean exitCode;

    public Command() {
        exitCode = false;
    }

    public Boolean getExitCode() {
        return exitCode;
    }

    public void setExitCode() {
        exitCode = true;
    }

    abstract public void execute(Ui ui, Storage storage, ResourceList resources) throws Exception;
}