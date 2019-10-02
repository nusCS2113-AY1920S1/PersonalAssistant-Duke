package duke.command;

import duke.commons.DukeException;
import duke.storage.BakingList;
import duke.storage.Storage;
import duke.ui.Ui;

/**
 * A command that restores any actions that have been previously undone using undo.
 */
public class RedoCommand extends Command {

    @Override
    public void execute(BakingList bakingList, Storage storage, Ui ui) throws DukeException {

    }

}
