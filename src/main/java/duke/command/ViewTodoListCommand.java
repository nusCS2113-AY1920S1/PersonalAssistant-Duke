package duke.command;

import duke.exception.DukeException;
import duke.list.GenericList;
import duke.order.Order;
import duke.order.OrderList;
import duke.storage.Storage;
import duke.ui.Ui;

public class ViewTodoListCommand extends Command<Order> {

    public ViewTodoListCommand() {
    }

    @Override
    public void execute(GenericList<Order> orderList, Ui ui, Storage storage) throws DukeException {
        String info = ((OrderList)orderList).todoListToString();
        System.out.println(info);
    }
}
