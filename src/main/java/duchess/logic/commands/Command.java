package duchess.logic.commands;

import duchess.exceptions.DuchessException;
import duchess.storage.Storage;
import duchess.storage.Store;
import duchess.ui.Ui;

/**
 * Represents a command given by the user, for Duchess to execute.
 */
public abstract class Command {
    protected boolean isExit;

    public boolean isExit() {
        return this.isExit;
    }

    public abstract void execute(Store store, Ui ui, Storage storage) throws DuchessException;
}