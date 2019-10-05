//package duke.logic.command.order;
//
//import duke.commons.DukeException;
//import duke.commons.core.Message;
//import duke.logic.command.commons.Undoable;
//import duke.logic.command.exceptions.CommandException;
//import duke.model.Model;
//import duke.model.order.Order;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * A command to remove an <code>Order</code> object from an <code>OrderList</code> object.
// */
//public class DeleteOrderCommand extends OrderCommand implements Undoable {
//    public static final String COMMAND_WORD = "remove";
//
//    private List<Order> toDelete = new ArrayList<>();
//    private List<Integer> toDeleteIndexes;
//
//    public DeleteOrderCommand(int... index) {
//        for (int i = 0; i < index.length; i++) {
//            toDeleteIndexes.add(index[i]);
//        }
//    }
//
//    public DeleteOrderCommand(int start, int end) {
//        for (int i = start; i <= end; i++) {
//            toDeleteIndexes.add(i);
//        }
//    }
//
//    public DeleteOrderCommand(List<Integer> toDeleteIndexes) {
//        this.toDeleteIndexes = toDeleteIndexes;
//    }
//
//    @Override
//    public void undo(Model model) throws CommandException {
//        for (int i = 0; i < toDeleteIndexes.size(); i++) {
//            model.getOrderList().add(toDeleteIndexes.get(i), toDelete.get(i));
//        }
//        storage.serialize(model);
//        ui.refreshOrderList(model.getOrderList(), model.getOrderList());
//        ui.showMessage("Undo: Remove order");
//    }
//
//    @Override
//    public void redo(Model model) throws CommandException {
//        try {
//            execute(bakingList);
//        } catch (CommandException e) {
//            e.printStackTrace();
//        }
//        ui.showMessage("Redo: Remove order");
//    }
//
//    @Override
//    public void execute(Model model) throws CommandException {
//        for (int i : toDeleteIndexes) {
//            if (i >= model.getOrderList().size() || i < 0) {
//                throw new DukeException(Message.MESSAGE_INVALID_RANGE);
//            }
//            toDelete.add(model.getOrderList().get(i));
//        }
//        model.getOrderList().removeAll(toDelete);
//        storage.serialize(model);
//        ui.refreshOrderList(model.getOrderList(), model.getOrderList());
//        ui.showMessage("Order removed");
//    }
//
//
//}