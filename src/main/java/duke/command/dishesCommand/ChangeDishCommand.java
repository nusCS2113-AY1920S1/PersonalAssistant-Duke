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

//@@author 9hafidz6
public class ChangeDishCommand extends Command {
    private String dishname;
    private int index;

    public ChangeDishCommand(String dishname, int index) {
        this.dishname = dishname;
        this.index = index;
    }

    /**
     * changes the name of the dish by index prints out to user dish has been change from (old name) to (new name)
     * @param fridge ingredients found in fridge
     * @param dishList list of dishes
     * @param ol list of orders
     * @param ui prints output for user
     * @param fs storage for fridge
     * @param os storage for order
     * @throws DukeException
     */
    @Override
    public void execute(Fridge fridge, DishList dishList, OrderList ol, Ui ui, FridgeStorage fs, OrderStorage os, RecipeStorage rs) throws DukeException {
        try {
            System.out.println("\t dish: " + dishList.getEntry(index - 1).getDishname());
            dishList.getEntry(index - 1).changeName(dishname);
            System.out.println("\t changed to: " + dishname);
        } catch (Exception e) {
            throw new DukeException("dish may not exist, try list command first to get the correct index you want");
        }
    }
}
