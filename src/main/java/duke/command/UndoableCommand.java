package duke.command;

import duke.commons.DukeException;
import duke.storage.BakingList;
import duke.storage.Storage;
import duke.ui.Ui;

public abstract class UndoableCommand extends Command {

    public abstract void undo(BakingList bakingList, Storage storage, Ui ui) throws DukeException;

    public abstract void redo(BakingList bakingList, Storage storage, Ui ui) throws DukeException;

}