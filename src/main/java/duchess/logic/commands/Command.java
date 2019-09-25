package duchess.logic.commands;

import duchess.storage.Storage;
import duchess.logic.commands.exceptions.DukeException;
import duchess.model.task.TaskList;
import duchess.ui.Ui;

public abstract class Command {
    protected boolean isExit;

    public boolean isExit() {
        return this.isExit;
    }

    public abstract void execute(TaskList taskList, Ui ui, Storage storage) throws DukeException;
}
