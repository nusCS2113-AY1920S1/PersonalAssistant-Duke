package duke.command.dishesCommand;

import duke.dish.Dish;
import duke.dish.DishList;
import duke.command.Cmd;
import duke.exception.DukeException;
import duke.list.GenericList;
import duke.storage.Storage;
import duke.ui.Ui;


public class ListDishCommand extends Cmd<Dish> {

    @Override
    public void execute(GenericList<Dish> dish1, Ui ui, Storage storage) throws DukeException {
        if (dish1.size() == 0) {
            throw new DukeException("No Dishes yet!");
        } else {
            System.out.println("\t Here are the dishes in your list:");
            for (int i = 1; i <= dish1.size(); i++) { // looping to print all the saved tasks
                ui.showDishes("\t " + i + "." + dish1.getEntry(i - 1).getDishname()
                        , dish1.getEntry(i - 1).getTotalNumberOfOrders());
            }
        }
    }
}