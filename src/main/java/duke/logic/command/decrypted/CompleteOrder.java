//package duke.logic.command.decrypted;
//
//
//import duke.commons.DukeException;
//import duke.logic.command.Command;
//import duke.logic.command.Undoable;
//import duke.model.order.Order;
//import duke.logic.parser.decrypted.CommandParser;
//import duke.storage.decrpted.BakingList;
//import duke.storage.decrpted.Storage;
//import duke.ui.Ui;
//
//import java.util.List;
//import java.util.Map;
//
///**
// * A command to set the status of an <code>Order</code> object to <code>COMPLETED</code>.
// */
//public class CompleteOrder extends Command implements Undoable {
//    private List<Order> orders;
//    private Map<String, List<String>> params;
//
//    /**
//     * Class constructor.
//     *
//     * @param params The parameters specifying details of the order.
//     */
//    public CompleteOrder(Map<String, List<String>> params) {
//        this.params = params;
//    }
//
//    @Override
//    public void undo(BakingList bakingList, Storage storage, Ui ui) throws DukeException {
//
//    }
//
//    @Override
//    public void redo(BakingList bakingList, Storage storage, Ui ui) throws DukeException {
//
//    }
//
//    @Override
//    public void execute(BakingList bakingList, Storage storage, Ui ui) throws DukeException {
//        orders = CommandParser.getOrders(bakingList.getOrderList(), params);
//        for (Order order : orders) {
//            order.setStatus(Order.Status.COMPLETED);
//        }
//        storage.serialize(bakingList);
//        ui.refreshOrderList(bakingList.getOrderList(), bakingList.getOrderList());
//    }
//}
