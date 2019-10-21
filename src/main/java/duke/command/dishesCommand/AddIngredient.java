package duke.command.dishesCommand;

import duke.Dishes.DishList;
import duke.command.Cmd;
import duke.exception.DukeException;
import duke.storage.Storage;
import duke.ui.Ui;

public class AddIngredient extends Cmd<DishList> {

    private String ingredient;
    private int Nb;

    public AddIngredient(String ingredient, int Nb) {
        this.ingredient = ingredient;
        this.Nb = Nb;
    }

    @Override
    public void execute(DishList dish1, Ui ui, Storage storage) throws DukeException {
        try {
            dish1.getDish(Nb - 1).addIngredients(ingredient);
            System.out.println("\t added ingredient: " + ingredient + "\n\t to dish: " + dish1.getDish(Nb - 1).getDishname());
        } catch (Exception e) {
            throw new DukeException("cannot add ingredient");
        }
    }
}