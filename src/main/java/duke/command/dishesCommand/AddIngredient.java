package duke.command.dishesCommand;

import duke.command.Command;
import duke.dish.DishList;
import duke.exception.DukeException;
import duke.fridge.Fridge;
import duke.ingredient.Ingredient;
import duke.order.OrderList;
import duke.storage.FridgeStorage;
import duke.storage.OrderStorage;
import duke.ui.Ui;

//Adds an ingredient to the dish
public class AddIngredient extends Command {

    private Ingredient ingredient;
    private int index;

    //constructor
    public AddIngredient(Ingredient ingredient, int index) {
        super();
        this.ingredient = ingredient;
        this.index = index;
    }

    //@@ Author Hafidz
    /**
     *
     * @param dishList
     * @param ui
     * @param storage
     * @throws DukeException
     */
    @Override
    public void execute(Fridge fridge, DishList dishList, OrderList ol, Ui ui, FridgeStorage fs, OrderStorage os) throws DukeException {
        try {
            dishList.getEntry(index - 1).addIngredients(ingredient);
            ui.showIngredients(ingredient,dishList.getEntry(index - 1));
        } catch (Exception e) {
            throw new DukeException("cannot add ingredient as the dish is not in list");
        }
    }
}
