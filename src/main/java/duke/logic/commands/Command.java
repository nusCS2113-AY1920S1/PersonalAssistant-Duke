package duke.logic.commands;

import duke.exceptions.DukeException;

import duke.models.LockerList;
import duke.storage.FileHandling;
import duke.ui.Ui;

public abstract class Command {
    public boolean isExit = false;

    public boolean isExit() {
        return this.isExit;
    }

    public abstract void execute(LockerList lockerList, Ui ui, FileHandling storage) throws DukeException;
}