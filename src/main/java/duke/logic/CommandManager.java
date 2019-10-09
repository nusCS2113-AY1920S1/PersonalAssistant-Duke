package duke.logic;

import duke.command.Command;
import duke.command.RedoCommand;
import duke.command.UndoCommand;
import duke.command.Undoable;
import duke.commons.DukeException;
import duke.storage.BakingList;
import duke.storage.Storage;
import duke.ui.Ui;

import java.util.ArrayList;
import java.util.List;

/**
 * Manager class of commands. An undo stack and redo stack is managed by CommandManager.
 */
public class CommandManager {
    private List<Undoable> undoStack = new ArrayList<>();
    private List<Undoable> redoStack = new ArrayList<>();
    private BakingList bakingList;
    private Storage storage;
    private Ui ui;

    public CommandManager(BakingList bakingList, Storage storage, Ui ui) {
        this.bakingList = bakingList;
        this.storage = storage;
        this.ui = ui;
    }

    /*
     * Runs a command. If the command is a subclass of <code>UndoableCommand</code>, puts it in undo stack.
     * @param command The command to be executed.
     * @throws DukeException if execution fails.
     */
    public void execute(Command command) throws DukeException {
        if (command instanceof UndoCommand) {
            undo();
        } else if (command instanceof RedoCommand) {
            redo();
        } else {
            command.execute(bakingList, storage, ui);
            if (command instanceof Undoable) {
                undoStack.add((Undoable) command);
            }
        }
    }

    private void undo() throws DukeException {
        if (undoStack.size() == 0) {
            throw new DukeException("No task to be undone.");
        }
        undoStack.get(undoStack.size() - 1).undo(bakingList, storage, ui);
        redoStack.add(undoStack.get(undoStack.size() - 1));
        undoStack.remove(undoStack.size() - 1);
    }

    private void redo() throws DukeException {
        if (redoStack.size() == 0) {
            throw new DukeException("No task to be redone.");
        }
        redoStack.get(redoStack.size() - 1).redo(bakingList, storage, ui);
        undoStack.add(redoStack.get(redoStack.size() - 1));
        redoStack.remove(redoStack.size() - 1);
    }
}
