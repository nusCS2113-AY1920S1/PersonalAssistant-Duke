package duke.commands;

import duke.DukeException;
import duke.Storage;
import duke.Ui;
import duke.components.SongList;
import duke.components.UndoRedoStack;

public class RedoCommand extends Command<SongList> {

    @Override
    public String execute(SongList object, Ui ui, Storage storage) throws DukeException {
        return null;
    }

    @Override
    public String execute(SongList songs, Ui ui, Storage storage, UndoRedoStack undoRedoStack) throws DukeException {
        if (!undoRedoStack.canRedo()) {
            return ui.formatRedo();
        }

        // can redo
        undoRedoStack.redo();
        storage.updateFile(undoRedoStack.getCurrentVersion());
        return ui.formatRedo(undoRedoStack.numOfRedoLeft());
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
