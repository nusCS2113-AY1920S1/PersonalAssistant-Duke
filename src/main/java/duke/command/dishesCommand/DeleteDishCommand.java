package duke.command.dishesCommand;

import duke.command.Command;
import duke.dish.DishList;
import duke.exception.DukeException;
import duke.fridge.Fridge;
import duke.order.OrderList;
import duke.storage.FridgeStorage;
import duke.storage.OrderStorage;
import duke.storage.RecipeStorage;
import duke.ui.Ui;

public class DeleteDishCommand extends Command {

    private int Nb;

    //constructor
    public DeleteDishCommand(int dishNb) {
        this.Nb = dishNb;
    }

    //@@ Author Hafidz
    /**
     *
     * @param dish1
     * @param ui
     * @param storage
     * @throws DukeException
     */
    @Override
    public void execute(Fridge fridge, DishList dishList, OrderList ol, Ui ui, FridgeStorage fs, OrderStorage os, RecipeStorage rs) throws DukeException {
        try {
            ui.showDeletedDIsh(dishList.getEntry(Nb - 1).getDishname());
            dishList.removeEntry(Nb - 1);
        } catch (Exception e) {
            throw new DukeException("dish does not exist");
        }
    }
}
