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

//@@author 9hafidz6
public class FindDishCommand extends Command {
    private String dishname;

    public FindDishCommand(String dishname) {
        this.dishname = dishname;
    }

    /**
     *  finds a dish in dishList that corresponds to dishname. iterate through list,if match, output to user
     *  else output, not found
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
            boolean flag = true;
            ui.showLine();
            System.out.println("\t dishes that contain, " + dishname + ":\n");
            for(int a = 0; a < dishList.size(); a++) {
                if(dishList.getEntry(a).getDishname().contains(dishname)) {
                    flag = false;
                    System.out.println("\t index:" + (a+1) + "\t dish:" + dishList.getEntry(a).getDishname());
                    System.out.println("\t " + dishList.getEntry(a).toString());
                }
            }
            if(flag) {
                System.out.println("\t No dishes found");
            }
            ui.showLine();
        } catch (Exception e) {
            throw new DukeException("error on find dish command");
        }
    }
}
