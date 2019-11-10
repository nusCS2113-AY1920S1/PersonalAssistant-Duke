package duke.logic.commands;

import duke.exceptions.DukeException;

import duke.models.LockerList;
import duke.storage.Storage;
import duke.ui.Ui;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {
    public boolean isExit = false;

    public boolean isExit() {
        return this.isExit;
    }

    public abstract void execute(LockerList lockerList, Ui ui, Storage storage) throws DukeException;
}