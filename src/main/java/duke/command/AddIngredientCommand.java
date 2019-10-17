package duke.command;
import duke.exception.DukeException;
import duke.ingredients.Ingredients;

import duke.recipebook.DishList;
import duke.recipebook.Dishes;
import duke.storage.Storage;
import duke.task.TaskList;
import duke.ui.Ui;

public class AddIngredientCommand extends Command
{
    private Ingredients ingredients;
    private Dishes dish;

    public AddIngredientCommand(Ingredients ingredients)
    {
        this.ingredients = ingredients;
    }


    @Override
    public void execute(DishList dish1, TaskList taskList, Ui ui, Storage storage) throws DukeException {

    }
}
