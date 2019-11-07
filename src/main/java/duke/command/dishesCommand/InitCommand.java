package duke.command.dishesCommand;

import duke.command.Command;
import duke.dish.Dish;
import duke.dish.DishList;
import duke.exception.DukeException;
import duke.fridge.Fridge;
import duke.ingredient.IngredientsList;
import duke.list.GenericList;
import duke.order.OrderList;
import duke.storage.FridgeStorage;
import duke.storage.OrderStorage;
import duke.storage.Storage;
import duke.ui.Ui;

public class InitCommand extends Command {

    public InitCommand() {
        //clears all the amount in dishes
    }

    //@@ Author Hafidz

    /**
     * this method gives user the option to either clear the list of dishes, dishList.clearList()
     * @param dishList list of ingredient
     * @param dishList list of dishes
     * @param ol list of orders
     * @param ui output messages for user
     * @param fs storage for fridge component
     * @param os storage for order component
     * @throws DukeException
     */
    @Override
    public void execute(Fridge fridge, DishList dishList, OrderList ol, Ui ui, FridgeStorage fs, OrderStorage os) throws DukeException {
        System.out.println("\t are you sure you want to clear list? [y/n]");
        String command = ui.readCommand();
        if(command.toLowerCase().equals("y")){
            dishList.clearList();
            System.out.println("\t LIST IS CLEARED");
        }
        else if(command.toLowerCase().equals("n")){
            System.out.println("\t LIST IS NOT CLEARED");
        }
        else {throw new DukeException("Please enter y or n after 'initialize' command");}
    }
}
