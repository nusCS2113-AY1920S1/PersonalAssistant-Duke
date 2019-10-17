package duke.recipeCommand;

import duke.exception.DukeException;
import duke.recipebook.DishList;
import duke.storage.Storage;
import duke.task.TaskList;
import duke.ui.Ui;


public class ListDishCommand extends RecipeCommand {

    @Override
    public void execute(DishList dish1, TaskList taskList, Ui ui, Storage storage) throws DukeException {
        if (DishList.getSize() == 0) {
            throw new DukeException("No Dishes yet!");
        } else {
            System.out.println("\t Here are the tasks in your list:");
            for (int i = 1; i <= DishList.getSize(); i++) { // looping to print all the saved tasks
                ui.showDishes("\t " + i + "." + DishList.getDish(i - 1).toString()
                        , DishList.getDish(i - 1).getTotalNumberOfOrders());
            }
        }
    }
}
