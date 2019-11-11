package duke.command.ingredientCommand;

import duke.command.Command;
import duke.list.DishList;
import duke.exception.DukeException;
import duke.fridge.Fridge;
import duke.ingredient.Ingredient;
import duke.list.OrderList;
import duke.storage.FridgeStorage;
import duke.storage.OrderStorage;
import duke.storage.RecipeStorage;
import duke.ui.Ui;

/**
 * Represents a specific {@link Command} for Ingredients to facilitate removing an ingredient
 * from the {@link Fridge}
 *
 * @author Sara Djambazovska
 */
public class DeleteCommand extends Command {

    private int ingredientIndex;

    /**
     * Constructor of the class {@link DeleteCommand}
     * Creates a new {@link DeleteCommand} command to remove an ingredient
     * from the {@link Fridge}
     *
     * @param ingredientIndex the index of the ingredient to be removed
     */
    public DeleteCommand(int ingredientIndex) {
        this.ingredientIndex = ingredientIndex;
    }

    @Override
    public void execute(Fridge fridge, DishList dl, OrderList ol, Ui ui, FridgeStorage fs, OrderStorage os, RecipeStorage rs) throws DukeException {
        assert fridge != null;
        assert fs != null;
        assert ui != null;
        ui.showLine();
        if (fridge.isEmpty())
            throw new DukeException("The fridge is empty, there is nothing to remove!");
        if (ingredientIndex <= fridge.numberOfIngredients() && ingredientIndex > 0) {
            Ingredient removed = fridge.useIngredient(ingredientIndex - 1);
            fs.update(); // updating the fridge storage
            ui.showRemovedIngredient(removed.toStringNoWarning(), fridge.numberOfIngredients());
        } else {
            String errorMessage = "Enter a valid ingredient index number after remove, between 1 and " + fridge.numberOfIngredients();
            throw new DukeException(errorMessage);
        }
        ui.showLine();
    }
}
