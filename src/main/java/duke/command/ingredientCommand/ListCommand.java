package duke.command.ingredientCommand;

import duke.command.Cmd;
import duke.exception.DukeException;
import duke.ingredient.Ingredient;
import duke.ingredient.IngredientsList;
import duke.list.GenericList;
import duke.storage.Storage;
import duke.ui.Ui;

/**
 * Represents a specific {@link Cmd} used to list all the {@link Ingredient}s in the {@link IngredientsList}.
 */
public class ListCommand<T> extends Cmd<T> {

    @Override
    public void execute(GenericList<T> IngredientsList, Ui ui, Storage storage) throws DukeException {
        if (IngredientsList.size() == 0) {
            throw new DukeException("No ingredients yet!");
        } else {
            System.out.println("\t Here are the ingredients in your list:");
            for (int i = 1; i <= IngredientsList.size(); i++) { // looping to print all the saved tasks
                ui.showTask("\t " + i + "." + IngredientsList.getEntry(i - 1).toString());
            }
        }
    }
}
