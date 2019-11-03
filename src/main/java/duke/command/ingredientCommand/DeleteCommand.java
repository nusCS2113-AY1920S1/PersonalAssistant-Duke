package duke.command.ingredientCommand;

import duke.command.Command;
import duke.exception.DukeException;
import duke.ingredient.Ingredient;
import duke.ingredient.IngredientsList;
import duke.list.GenericList;
import duke.storage.Storage;
import duke.ui.Ui;

import java.io.IOException;

/**
 * Represents a specific {@link Command} used to delete a {@link Ingredient} from the {@link IngredientsList}.
 */
public class DeleteCommand<T> extends Command<T> {
    private int taskNb;

    public DeleteCommand(int taskNb) {
        this.taskNb = taskNb;
    }

    @Override
    public void execute(GenericList<T> IngredientsList, Ui ui, Storage storage) throws DukeException {
        if (taskNb <= IngredientsList.size() && taskNb > 0) {
            T removed = IngredientsList.removeEntry(taskNb - 1);

            try {
                storage.removeFromFile(taskNb - 1);
            } catch (IOException e) {
                throw new DukeException("Error while deleting the ingredient from the hard disc");
            }
            ui.showRemovedIngredient(removed.toString(), IngredientsList.size());
        } else {
            throw new DukeException("Enter a valid ingredient index number after delete, between 1 and " + IngredientsList.size());
        }
    }
}
