package duke.command.orderCommand;

import duke.command.Command;
import duke.dish.DishList;
import duke.exception.DukeException;
import duke.fridge.Fridge;
import duke.order.Order;
import duke.order.OrderList;
import duke.storage.FridgeStorage;
import duke.storage.OrderStorage;
import duke.storage.RecipeStorage;
import duke.ui.Ui;

/**
 * @author VirginiaYu
 *
 * Represents a specific {@link Command} used to clear all the {@link Order}s in the order list.
 *
 */
public class InitOrderListCommand extends Command {

    /**
     * the constructor method of {@link InitOrderListCommand}
     */
    public InitOrderListCommand() {
    }

    /**
     * Public method used to initialize the orderList, and clear all content in storage file on the hard disc.
     * Print out corresponding info if initializing successfully
     *
     * @param orderList the {@link OrderList} to be expanded
     * @param ui       {@link Ui} used for printing the order output
     * @param orderStorage  {@link OrderStorage} writes in the file on the hard disc
     * @throws DukeException Error while initializing the order list, or clearing the file on the hard disc
     */
    @Override
    public void execute(Fridge fridge, DishList dl, OrderList orderList, Ui ui, FridgeStorage fs, OrderStorage orderStorage, RecipeStorage rs) throws DukeException {
        System.out.println("\t Are you sure you want to clear all orders in the order list? [y/n]");
        String command = ui.readCommand();
        if(command.toLowerCase().equals("y")){

            orderList.clearList();
            orderStorage.clearInfoForFile();
            orderList.initTodoList();

            ui.showLine();
            System.out.println("\t ORDER LIST CLEARED");
            System.out.println("\t TODAY TODO LIST CLEARED");
            System.out.println("\n\t Continue by adding order. Template:");
            System.out.println("\t add [-d ORDER_DATE-(dd/mm/yyyy)] -n DISH1_NAME[*DISH_AMOUNT], DISH2_NAME[*DISH_AMOUNT]");
            ui.showLine();
        } else {
            ui.showLine();
            System.out.println("\t ORDER LIST NOT CLEARED");
            System.out.println("\n\t Continue by adding, cancelling, altering, listing order.");
            System.out.println("\t Type 'template' to see command format.");
            ui.showLine();
        }
    }

}
