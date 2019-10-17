package duke.recipeCommand;

import duke.exception.DukeException;
import duke.recipebook.Dishes;
import duke.recipebook.DishList;
import duke.storage.Storage;
import duke.task.TaskList;
import duke.ui.Ui;

public class AddDishCommand extends RecipeCommand {

    private Dishes dish;
    private int amount;
    private int Nb;

    public AddDishCommand(Dishes dish, int amount) {
        this.dish = dish;
        this.amount = amount;
    }

    @Override
    public void execute(DishList dish1, TaskList taskList, Ui ui, Storage storage) throws DukeException {

        try {
            dish1.addDish(dish); // add dish into list found in dishes class

            for( int i = 1; i < DishList.getSize(); i++) {
                if(DishList.getDish(i).getDishname().equals(dish.toString())){
                    Nb = i;
                    break;
                }
            }
            dish1.getDish(Nb).setNumberOfOrders(amount);
            ui.showAddedDishes(dish.toString(), DishList.getDish(Nb).getTotalNumberOfOrders());
        } catch (Exception e) {
            throw new DukeException("unable to add dish");
        }
    }
}
