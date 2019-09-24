package duke.command;

import duke.commons.DukeException;
import duke.order.Order;
import duke.parser.TimeParser;
import duke.storage.BakingList;
import duke.storage.Storage;
import duke.ui.Ui;

import java.util.List;
import java.util.Map;

public class AddOrderCommand extends UndoableCommand {
    Map<String, List<String>> params;
    protected Order order;

    public AddOrderCommand(Map<String, List<String>> params) {
        this.params = params;
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

    private Order getOrder() throws DukeException {
        if (!params.containsKey("name") || !params.containsKey("contact") || !params.containsKey("by") || !params.containsKey("item")) {
            throw new DukeException("Must have name, contact, deadline & item.");
        }

        Order order = new Order(params.get("name").get(0),
                params.get("contact").get(0),
                TimeParser.convertStringToDate(params.get("by").get(0)));

        if (params.containsKey("rmk")) {
            order.setRemarks(params.get("rmk").get(0));
        }

        for (String item : params.get("item")) {
            String[] itemAndQty = item.split(",");
            if (itemAndQty.length < 2) {
                throw new DukeException("Must have item name and quantity");
            }
            if (itemAndQty[0].strip().equals("") || itemAndQty[1].strip().equals("")) {
                throw new DukeException("Item name and quantity should not be empty");
            }
            try {
                order.addItem(itemAndQty[0].strip(), Integer.parseInt(itemAndQty[1].strip()));
            } catch (NumberFormatException e) {
                throw new DukeException("Quantity should be an integer");
            }
        }
        return order;
    }

    private void addOrder(Order order, BakingList bakingList) {
        bakingList.getOrderList().add(0, order);
    }
}
