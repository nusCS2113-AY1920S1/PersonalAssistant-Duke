package duke.command.dishesCommand;

import duke.Dishes.DishList;
import duke.command.Cmd;
import duke.exception.DukeException;
import duke.storage.Storage;
import duke.ui.Ui;


public class ListDishCommand extends Cmd<DishList> {

    @Override
    public void execute(DishList dish1, Ui ui, Storage storage) throws DukeException {
        if (dish1.getSize() == 0) {
            throw new DukeException("No Dishes yet!");
        } else {
            System.out.println("\t Here are the dishes in your list:");
            for (int i = 1; i <= dish1.getSize(); i++) { // looping to print all the saved tasks
                ui.showDishes("\t " + i + "." + dish1.getDish(i - 1).toString()
                        , dish1.getDish(i - 1).getTotalNumberOfOrders());
            }
        }
    }

}
