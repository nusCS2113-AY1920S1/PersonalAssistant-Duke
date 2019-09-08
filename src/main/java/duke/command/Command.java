package duke.command;

import duke.dukeexception.DukeException;
import duke.task.TaskList;

public abstract class Command {
    protected boolean isExit;

    public boolean isExit() {
        return this.isExit;
    }

    public abstract void execute(TaskList taskList, Ui ui, Storage storage) throws DukeException;
}
