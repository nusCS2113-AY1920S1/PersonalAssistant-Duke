package duke.command.ingredientCommand;

import duke.command.Cmd;
import duke.exception.DukeException;
import duke.ingredient.IngredientsList;
import duke.list.GenericList;
import duke.storage.Storage;
import duke.ui.Ui;

/**
 * Represents a specific {@link Cmd} used to mark a {@link IngredientsList} as done.
 */
public class DoneCommand<T> extends Cmd<T> {
    private int taskNb;

    public DoneCommand(int taskNb) {
        this.taskNb = taskNb;
    }

    @Override
    public void execute(GenericList<T> IngredientsList, Ui ui, Storage storage) throws DukeException {
        if (taskNb < IngredientsList.size() && taskNb >= 0) {
            ((duke.ingredient.IngredientsList)IngredientsList).removeEntry(taskNb);
            ui.showMarkDone(IngredientsList.getEntry(taskNb).toString());
            storage.changeContent(taskNb);
        } else {
            throw new DukeException("Enter a valid task number after done, between 1 and " + IngredientsList.size());
        }
    }
}
