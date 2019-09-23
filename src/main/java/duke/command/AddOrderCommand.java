package duke.command;

import duke.commons.DukeException;
import duke.order.Order;
import duke.storage.BakingList;
import duke.storage.Storage;
import duke.ui.Ui;

public class AddOrderCommand extends UndoableCommand {
    protected Order order;

    public AddOrderCommand(Order order) {
        this.order = order;
    }

    @Override
    public void execute(BakingList bakingList, Storage storage, Ui ui) throws DukeException {
        //ArrayList<Order> orders = new ArrayList<>();
        //orders.add(order);
        bakingList.getOrderList().add(order);
        storage.serialize(bakingList);
        ui.refreshOrderList(bakingList.getOrderList(), bakingList.getOrderList());
    }

    @Override
    public void undo(BakingList bakingList, Storage storage, Ui ui) throws DukeException {

    }

    @Override
    public void redo(BakingList bakingList, Storage storage, Ui ui) throws DukeException {

    }
}
