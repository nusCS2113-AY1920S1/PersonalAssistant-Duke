package duke.command;

import duke.commons.DukeException;
import duke.storage.BakingList;
import duke.storage.Storage;
import duke.ui.Ui;

/**
 * A command that reverses the action of an earlier action.
 */
public class UndoCommand extends Command {

    @Override
    public void execute(BakingList bakingList, Storage storage, Ui ui) throws DukeException {

    }
}
