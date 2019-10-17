package duke.command;
import duke.exception.DukeException;
import duke.ingredients.Ingredients;

import duke.recipebook.dishes;
import duke.recipebook.dishlist;
import duke.storage.Storage;
import duke.task.TaskList;
import duke.ui.Ui;

public class AddIngredientCommand extends Command
{
    private Ingredients ingredients;
    private dishes dish;

    public AddIngredientCommand(Ingredients ingredients)
    {
        this.ingredients = ingredients;
    }




}
