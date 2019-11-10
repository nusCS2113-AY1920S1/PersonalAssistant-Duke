package duke.command.ingredientCommand;

import duke.command.Command;
import duke.dish.DishList;
import duke.exception.DukeException;
import duke.fridge.Fridge;
import duke.ingredient.Ingredient;
import duke.order.OrderList;
import duke.storage.FridgeStorage;
import duke.storage.OrderStorage;
import duke.storage.RecipeStorage;
import duke.ui.Ui;

/**
 * Represents a specific {@link Command} for Ingredients to facilitate adding an ingredient
 * to the {@link Fridge}
 *
 * @author Sara Djambazovska
 */
public class AddCommand extends Command {

    private Ingredient ingredient;

    /**
     * Constructor of the class {@link AddCommand}
     * Creates a new {@link AddCommand} with the indicated ingredient to be added,
     * if there is an ingredient in the {@link Fridge} with the same name and expiry date, only the amount is increased by the ingredient's amount,
     * otherwise, the ingredient is added as a new entry in the fridge. Furthermore, if the ingredient to be added is expired,
     * user input is needed to proceed with adding it where applicable.
     *
     * @param ingredient the ingredient to be added, specified by the name, amount and date
     */
    public AddCommand(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    @Override
    public void execute(Fridge fridge, DishList dishList, OrderList orderList, Ui ui, FridgeStorage fridgeStorage,
                        OrderStorage orderStorage, RecipeStorage rs) throws DukeException {
        if (ingredient.isExpired()) {
            ui.showDialogAddingExpired();
            String confirmation = ui.readCommand();
            if (!confirmation.trim().equalsIgnoreCase("yes")) {
                ui.showIngredientTemplate();
                return;
            }
        }
        fridge.addIngredient(ingredient);
        ui.showAddedIngredient(fridge.getIngredient(ingredient));
        fridgeStorage.update();
    }
}
