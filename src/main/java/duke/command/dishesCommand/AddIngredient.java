package duke.command.dishesCommand;

import duke.command.ingredientCommand.AddCommand;
import duke.dish.Dish;
import duke.dish.DishList;
import duke.command.Cmd;
import duke.exception.DukeException;
import duke.ingredient.Ingredient;
import duke.ingredient.IngredientsList;
import duke.list.GenericList;
import duke.storage.Storage;
import duke.ui.Ui;

public class AddIngredient extends AddCommand<Dish> {

    private Ingredient ingredient;

    private int index;

    public AddIngredient(Ingredient ingredient, int index) {
        super();
        this.ingredient = ingredient;
        this.index = index;
    }

    @Override
    public void execute(GenericList<Dish> dishList, Ui ui, Storage storage) throws DukeException {
        try {
            dishList.getEntry(index - 1).addIngredients(ingredient);
            ui.showIngredients(ingredient,dishList.getEntry(index - 1));
        } catch (Exception e) {
            throw new DukeException("cannot add ingredient");
        }
    }
}
