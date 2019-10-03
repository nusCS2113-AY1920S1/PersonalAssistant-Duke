package duke.command.order;

import duke.command.Undoable;
import duke.commons.DukeException;
import duke.entities.Order;
import duke.parser.decrypted.CommandParser;
import duke.storage.BakingList;
import duke.storage.Storage;
import duke.ui.Ui;

import java.util.List;
import java.util.Map;

/**
 * A command to edit the properties of an <code>Order</code> object.
 */
public class EditOrder extends OrderCommand implements Undoable {

    public static final String COMMAND_WORD = "edit";
    private Map<String, List<String>> params;
    private Order order;
    private Order unmodifiedOrder = new Order();

    /**
     * Class constructor.
     *
     * @param params the parameters specifying details of the order.
     */
    public EditOrder(Map<String, List<String>> params) throws DukeException {
        if (!(params.containsKey("i") == !params.containsKey("id"))) {
            throw new DukeException("Please specify order ID or index");
        }

        this.params = params;
    }

    public EditOrder() {
    }

    @Override
    public void undo(BakingList bakingList, Storage storage, Ui ui) throws DukeException {
        copyOrder(order, unmodifiedOrder);
        storage.serialize(bakingList);
        ui.refreshOrderList(bakingList.getOrderList(), bakingList.getOrderList());
    }

    @Override
    public void redo(BakingList bakingList, Storage storage, Ui ui) throws DukeException {
        order = getOrder(bakingList);
        CommandParser.modifyOrdrer(params, order);
        storage.serialize(bakingList);
        ui.refreshOrderList(bakingList.getOrderList(), bakingList.getOrderList());
    }

    @Override
    public void execute(BakingList bakingList, Storage storage, Ui ui) throws DukeException {
        order = getOrder(bakingList);
        copyOrder(unmodifiedOrder, order);
        CommandParser.modifyOrdrer(params, order);
        storage.serialize(bakingList);
        ui.refreshOrderList(bakingList.getOrderList(), bakingList.getOrderList());
    }

    private Order getOrder(BakingList bakingList) throws DukeException {
        if (params.containsKey(("i"))) {
            return getOrderByIndex(bakingList, params.get("i").get(0));
        } else {
            return getOrderById(bakingList, params.get("id").get(0));
        }
    }

    private Order getOrderById(BakingList bakingList, String i) throws DukeException {
        long id;
        try {
            id = Long.parseLong(params.get("id").get(0));
        } catch (NumberFormatException e) {
            throw new DukeException("Please provide a valid order ID");
        }

        for (Order order : bakingList.getOrderList()) {
            if (order.getId() == id) {
                return order;
            }
        }

        throw new DukeException("Unknown ID");
    }

    private Order getOrderByIndex(BakingList bakingList, String i) throws DukeException {
        int index;
        try {
            index = Integer.parseInt(params.get("i").get(0)) - 1;
        } catch (NumberFormatException e) {
            throw new DukeException("Please provide a valid index");
        }

        if (index < 0 || index >= bakingList.getOrderList().size()) {
            throw new DukeException("Index out of bound.");
        }

        return bakingList.getOrderList().get(index);
    }

    private void copyOrder(Order to, Order from) {
        to.setStatus(from.getStatus());
        to.setDeliveryDate(from.getDeliveryDate());
        to.setCustomerContact(from.getCustomerContact());
        to.setCustomerName(from.getCustomerName());
        to.setRemarks(from.getRemarks());
        to.setId(from.getId());
        to.getItems().clear();
        for (Map.Entry<String, Integer> entry : from.getItems().entrySet()) {
            to.addItem(entry.getKey(), entry.getValue());
        }
    }

}
