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
public class FindDishCommand extends Command {
    private String dishname;

    public FindDishCommand(String dishname) {
        this.dishname = dishname;
    }

    /**
     *  finds a dish in dishList that corresponds to dishname. iterate through list,if match, output to user
     *  else output, not found
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
        boolean flag = true;
        ui.showLine();
        System.out.println("dishes that contain, " + dishname + ":\n");
        for(int a = 0; a < dishList.size(); a++) {
            if(dishList.getEntry(a).getDishname().contains(dishname)) {
                flag = false;
                System.out.println("\t index:" + (a+1) + "\t dish:" + dishList.getEntry(a).getDishname());
            }
        }
        if(flag) {
            System.out.println("\t No dishes found");
        }
        ui.showLine();
    }
}
