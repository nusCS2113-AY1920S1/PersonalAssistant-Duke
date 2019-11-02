package duke.command.orderCommand;

import duke.command.Cmd;
import duke.dish.Dish;
import duke.exception.DukeException;
import duke.list.GenericList;
import duke.order.Order;
import duke.storage.Storage;
import duke.ui.Ui;

public class InitOrderListCommand extends Cmd<Order> {

    public InitOrderListCommand() {
    }

    @Override
    public void execute(GenericList<Order> orderList, Ui ui, Storage orderStorage) throws DukeException {
        System.out.println("\t Are you sure you want to clear all orders in the order list? [Y/N]");
        String command = ui.readCommand();
        if(command.toLowerCase().equals("y")){
            orderList.clearList();
            orderStorage.clearInfoForFile();
            ui.showLine();
            System.out.println("\t ORDER LIST CLEARED");
            ui.showLine();
        }
        else if(command.toLowerCase().equals("n")){
            ui.showLine();
            System.out.println("\t ORDER LIST NOT CLEARED");
            ui.showLine();
        }
    }

}
