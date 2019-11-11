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
 * Represents a specific {@link Command} for Ingredients to facilitate using a certain amount of an ingredient from the {@link Fridge}
 *
 * @author Sara Djambazovska
 */
public class UseCommand extends Command {
    private Ingredient toUse;

    /**
     * Constructor of the class {@link UseCommand}
     * Creates a new {@link UseCommand} with the indicated ingredient to use
     *
     * @param ingredient the ingredient to be used, specified by the name and amount, date is neglected
     */
    public UseCommand(Ingredient ingredient) throws DukeException {
        assert ingredient != null;
        toUse = ingredient;
    }

    @Override
    public void execute(Fridge fridge, DishList dishList, OrderList orderList, Ui ui, FridgeStorage fridgeStorage, OrderStorage orderStorage, RecipeStorage rs) throws DukeException {
        assert fridge != null;
        assert fridgeStorage != null;
        assert ui != null;
        if (toUse.getAmount() == 0)
            throw new DukeException("Enter a valid amount to use, at least 1!");
        ui.showLine();
        if (fridge.useIngredient(toUse)) {
            ui.show("Great you used " + toUse.toStringWithoutDate()); // if the fridge was able to provide the required amount of the ingredient
            fridgeStorage.update(); //updating the storage
        } else {
            ui.show("There is not a sufficient amount of " + toUse.getName() + " that is not expired, maybe you could buy some first? ");
        }
        ui.showLine();
    }
}
