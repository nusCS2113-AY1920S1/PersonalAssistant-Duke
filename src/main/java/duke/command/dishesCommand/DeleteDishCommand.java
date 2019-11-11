package duke.command.dishesCommand;

import duke.command.Command;
import duke.list.DishList;
import duke.exception.DukeException;
import duke.fridge.Fridge;
import duke.list.OrderList;
import duke.storage.FridgeStorage;
import duke.storage.OrderStorage;
import duke.storage.RecipeStorage;
import duke.ui.Ui;

public class DeleteDishCommand extends Command {

    private int index;

    //constructor
    public DeleteDishCommand(int dishNb) {
        this.index = dishNb;
    }

    //@@ Author 9hafidz6
    /**
     * Deletes a dish from dishlist using given index
     * @param fridge ingredients found in fridge
     * @param dishList list of dishes
     * @param ol list of orders
     * @param ui prints output for user
     * @param fs storage for fridge
     * @param os storage for order
     * @param rs storage for recipe
     * @throws DukeException
     */
    @Override
    public void execute(Fridge fridge, DishList dishList, OrderList ol, Ui ui, FridgeStorage fs, OrderStorage os, RecipeStorage rs) throws DukeException {
        try {
            ui.showDeletedDIsh(dishList.getEntry(index - 1).getDishname());
            dishList.removeEntry(index - 1);
            rs.removeFromFile(index - 1);
        } catch (Exception e) {
            throw new DukeException("dish does not exist");
        }
    }
}
