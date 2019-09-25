package duke.command;

import duke.commons.DukeException;
import duke.entities.Order;
import duke.parser.CommandParser;
import duke.parser.TimeParser;
import duke.storage.BakingList;
import duke.storage.Storage;
import duke.ui.Ui;

import java.util.List;
import java.util.Map;

public class AddOrderCommand extends UndoableCommand {
    private Map<String, List<String>> params;
    private Order order;

    public AddOrderCommand(Map<String, List<String>> params) throws DukeException {
        this.params = params;
        checkParameters();
    }

    @Override
    public void execute(BakingList bakingList, Storage storage, Ui ui) throws DukeException {
        order = getOrder();
        addOrder(order, bakingList);
        storage.serialize(bakingList);
        ui.refreshOrderList(bakingList.getOrderList(), bakingList.getOrderList());
    }

    @Override
    public void undo(BakingList bakingList, Storage storage, Ui ui) throws DukeException {

    }

    @Override
    public void redo(BakingList bakingList, Storage storage, Ui ui) throws DukeException {

    }

    private void checkParameters() throws DukeException {
        if (!params.containsKey("name") || !params.containsKey("contact")
                || !params.containsKey("by") || !params.containsKey("item")) {
            throw new DukeException("Must have name, contact, deadline & item.");
        }
    }

    private Order getOrder() throws DukeException {
        Order order = new Order(params.get("name").get(0),
                params.get("contact").get(0),
                TimeParser.convertStringToDate(params.get("by").get(0)));

        if (params.containsKey("rmk")) {
            order.setRemarks(params.get("rmk").get(0));
        }

        CommandParser.addItemsToOrder(params, order);

        return order;
    }

    private void addOrder(Order order, BakingList bakingList) {
        bakingList.getOrderList().add(0, order);
    }
}
