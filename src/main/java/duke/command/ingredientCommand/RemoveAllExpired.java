package duke.command.ingredientCommand;

import duke.command.Command;
import duke.dish.DishList;
import duke.exception.DukeException;
import duke.fridge.Fridge;
import duke.ingredient.IngredientsList;
import duke.order.OrderList;
import duke.storage.FridgeStorage;
import duke.storage.OrderStorage;
import duke.storage.RecipeStorage;
import duke.ui.Ui;

/**
 * Represents a specific {@link Command} for Ingredients to facilitate removing all of the expired ingredients
 * from the {@link Fridge}
 * @author Sara Djambazovska
 */
public class RemoveAllExpired extends Command {

    private Fridge fridge;
    /**
     * Constructor of the class {@link RemoveAllExpired}
     * Creates a new {@link RemoveAllExpired} command to remove all of the expired ingredients
     * from the {@link Fridge} passed as a parameter
     * @param fridge the {@link Fridge} whose expired ingredients should be removed
     */
    public RemoveAllExpired(Fridge fridge) {
        this.fridge = fridge;
    }

    @Override
    public void execute(Fridge fridge, DishList dl, OrderList ol, Ui ui, FridgeStorage fs, OrderStorage os,  RecipeStorage rs) throws DukeException {
        if (this.fridge.hasExpiredIngredients()) {
            IngredientsList expired = this.fridge.removeExpired();
            fs.update(); //updating the fridgeStorage
            ui.show(" Removed " + expired.toString());
        } else
            throw new DukeException("Seems like you don't have any expired ingredients in the fridge!");
    }
}
