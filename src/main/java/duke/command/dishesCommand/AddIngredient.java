package duke.command.dishesCommand;

import duke.command.Command;
import duke.dish.Dish;
import duke.exception.DukeException;
import duke.ingredient.Ingredient;
import duke.list.GenericList;
import duke.storage.Storage;
import duke.ui.Ui;

public class AddIngredient extends Command<Dish> {

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
            throw new DukeException("cannot add ingredient as the dish is not in list");
        }
    }
}
