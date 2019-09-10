package duke.command;

import duke.commons.DukeException;
import duke.storage.Storage;
import duke.task.TaskList;
import duke.ui.Ui;

public interface Undoable {
    public void undo(TaskList tasks, Storage storage, Ui ui) throws DukeException;

    public void redo(TaskList tasks, Storage storage, Ui ui) throws DukeException;
}
