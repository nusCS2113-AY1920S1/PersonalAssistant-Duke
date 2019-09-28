package duke.command.recipe;

import duke.command.Command;
import duke.command.UndoableCommand;
import duke.commons.DukeException;
import duke.entities.recipe.Recipe;
import duke.logic.Duke;
import duke.storage.BakingList;
import duke.storage.Storage;
import duke.ui.Ui;

import java.util.List;
import java.util.Map;

public class DeleteRecipeCommand extends UndoableCommand {

    private Recipe recipe;
    private int index;
    private Map<String, List<String>> params;

    public DeleteRecipeCommand (Map<String, List<String>> params) throws DukeException {
        this.params = params;

    }


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

    @Override
    public void undo(BakingList bakingList, Storage storage, Ui ui) throws DukeException {

    }

    @Override
    public void redo(BakingList bakingList, Storage storage, Ui ui) throws DukeException {

    }
}
