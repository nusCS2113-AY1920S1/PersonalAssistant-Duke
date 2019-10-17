package duke.recipeCommand;

import duke.command.AddCommand;
import duke.exception.DukeException;
import duke.recipebook.DishList;
import duke.storage.Storage;
import duke.task.TaskList;
import duke.ui.Ui;

public class AddIngredientCommand extends RecipeCommand {

    private String ingredient;
    private int Nb;

    public AddIngredientCommand(String ingredient, int Nb) {
        this.ingredient = ingredient;
        this.Nb = Nb;
    }

    @Override
    public void execute(DishList dish1, TaskList taskList, Ui ui, Storage storage) throws DukeException {
        try {
            DishList.getDish(Nb - 1).addIngredients(ingredient);
            System.out.println("added " + ingredient + " to " + dish1.getDish(Nb - 1));
        } catch (Exception e) {
            throw new DukeException("cannot add ingredient");
        }
    }
}
