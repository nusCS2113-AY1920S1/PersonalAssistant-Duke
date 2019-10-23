package duke.command.dishesCommand;

import duke.command.ingredientCommand.AddCommand;
import duke.dish.DishList;
import duke.command.Cmd;
import duke.exception.DukeException;
import duke.ingredient.Ingredient;
import duke.ingredient.IngredientsList;
import duke.storage.Storage;
import duke.ui.Ui;

public class AddIngredient extends AddCommand<Ingredient> {

    private Ingredient ingredient;
    private int Nb;

    public AddIngredient(Ingredient ingredient, int Nb) {
        super(ingredient);
        this.ingredient = ingredient;
        this.Nb = Nb;
    }


    public void execute(DishList dishList, Ui ui, Storage storage) throws DukeException {
        try {
            dishList.getEntry(Nb - 1).addIngredients(ingredient);
            System.out.println("\t added ingredient: " + ingredient + "\n\t to dish: " + dishList.getEntry(Nb - 1).getDishname());
        } catch (Exception e) {
            throw new DukeException("cannot add ingredient");
        }
    }
}