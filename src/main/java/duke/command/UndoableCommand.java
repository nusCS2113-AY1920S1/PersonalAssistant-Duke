package duke.command;

import duke.commons.DukeException;
import duke.storage.Storage;
import duke.task.TaskList;
import duke.ui.Ui;

public abstract class UndoableCommand extends Command {

    public abstract void undo(TaskList tasks, Storage storage, Ui ui) throws DukeException;

    public abstract void redo(TaskList tasks, Storage storage, Ui ui) throws DukeException;
}
