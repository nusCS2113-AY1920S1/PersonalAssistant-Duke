package duke.command.dishesCommand;

import duke.command.Command;
import duke.dish.DishList;
import duke.exception.DukeException;
import duke.fridge.Fridge;
import duke.order.OrderList;
import duke.storage.FridgeStorage;
import duke.storage.OrderStorage;
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
     *
     * @param fridge
     * @param dishList
     * @param ol
     * @param ui
     * @param fs
     * @param os
     * @throws DukeException
     */
    @Override
    public void execute(Fridge fridge, DishList dishList, OrderList ol, Ui ui, FridgeStorage fs, OrderStorage os) throws DukeException {
        System.out.println("\t dish " + dishList.getEntry(index - 1).getDishname() + "\n");
        dishList.getEntry(index - 1).changeName(dishname);
        System.out.println("\t changed to " + dishname);
    }
}
