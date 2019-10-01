package duke.command.recipe;

import duke.command.Command;
import duke.commons.DukeException;
import duke.storage.BakingList;
import duke.storage.Storage;
import duke.ui.Ui;

public class AddRecipeCommand extends Command {
    /**
     * Execute the command.
     *
     * @param bakingList A BakingList.
     * @param storage    A Storage object which specifies the location of the data.
     * @param ui         A Ui object capable of controlling GUI.
     * @throws DukeException If the execution fails.
     */
    @Override
    public void execute(BakingList bakingList, Storage storage, Ui ui) throws DukeException {

    }
}
