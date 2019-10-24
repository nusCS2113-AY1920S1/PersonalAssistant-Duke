package duke.commands;

import duke.DukeException;
import duke.Storage;
import duke.Ui;
import duke.components.SongList;
import duke.components.UndoRedoStack;

public class UndoCommand extends Command<SongList> {

    @Override
    public String execute(SongList object, Ui ui, Storage storage) throws DukeException {
        return null;
    }

    @Override
    public String execute(SongList songs, Ui ui, Storage storage, UndoRedoStack undoRedoStack) throws DukeException {
        if (!undoRedoStack.canUndo()) {
            // cannot undo
            return ui.formatUndo();
        }

        // Can undo
        undoRedoStack.undo();
        storage.updateFile(undoRedoStack.getCurrentVersion());
        return ui.formatUndo(undoRedoStack.getCurrentVersionIndex());
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
