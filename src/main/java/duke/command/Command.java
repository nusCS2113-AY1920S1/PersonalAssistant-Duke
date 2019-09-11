package duke.command;

import duke.exceptions.DukeException;
import duke.storage.FileHandling;
import duke.tasks.TaskList;
import duke.ui.Ui;

public abstract class Command {
    public boolean isExit = false;

    public boolean isExit() {
        return this.isExit;
    }

    public abstract void execute(TaskList tasks, Ui ui, FileHandling storage) throws DukeException;
}