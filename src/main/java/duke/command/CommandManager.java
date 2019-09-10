package duke.command;

import duke.commons.DukeException;
import duke.storage.Storage;
import duke.task.TaskList;
import duke.ui.Ui;

import java.util.ArrayList;
import java.util.List;

public class CommandManager {
    private List<Undoable> undoStack = new ArrayList<>();
    private List<Undoable> redoStack = new ArrayList<>();
    private TaskList tasks;
    private Storage storage;
    private Ui ui;

    public CommandManager(TaskList tasks, Storage storage, Ui ui) {
        this.tasks = tasks;
        this.storage = storage;
        this.ui = ui;
    }

    public void execute(Command command) throws DukeException {
        if (command instanceof UndoCommand) {
            undo();
        } else {
            command.execute(tasks, storage, ui);
            if (command instanceof Undoable) undoStack.add((Undoable) command);
        }
    }

    private void undo() throws DukeException {
        if (undoStack.size() == 0) {
            throw new DukeException("No task to be undone.");
        }
        undoStack.get(undoStack.size() - 1).undo(tasks, storage, ui);
        undoStack.remove(undoStack.size() - 1);
    }
}
