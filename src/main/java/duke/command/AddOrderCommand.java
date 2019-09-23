package duke.command;

import duke.commons.DukeException;
import duke.order.Order;
import duke.storage.Storage;
import duke.task.TaskList;
import duke.ui.Ui;

import java.util.ArrayList;

public class AddOrderCommand extends UndoableCommand {
    protected Order order;

    public AddOrderCommand(Order order) {
        this.order = order;
    }

    @Override
    public void execute(TaskList tasks, Storage storage, Ui ui) throws DukeException {
        ArrayList<Order> orders = new ArrayList<>();
        orders.add(order);
        ui.refreshOrderList(orders, orders);
    }

    @Override
    public void undo(TaskList tasks, Storage storage, Ui ui) throws DukeException {

    }

    @Override
    public void redo(TaskList tasks, Storage storage, Ui ui) throws DukeException {

    }
}
