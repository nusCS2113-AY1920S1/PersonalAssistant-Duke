package duke.logic.command.order;

import duke.commons.core.Message;
import duke.commons.core.index.Index;
import duke.commons.util.CollectionUtil;
import duke.logic.command.commons.CommandResult;
import duke.logic.command.commons.Undoable;
import duke.logic.command.exceptions.CommandException;
import duke.model.Model;
import duke.model.commons.Customer;
import duke.model.commons.Product;
import duke.model.order.Order;

import java.util.*;

import static duke.commons.util.CollectionUtil.requireAllNonNull;
import static java.util.Objects.requireNonNull;

/**
 * A command to edit the details of an existing order.
 */
public class EditOrderCommand extends OrderCommand implements Undoable {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Order: %1$s";

    private final Index index;
    private final EditOrderDescriptor editOrderDescriptor;

    public EditOrderCommand(Index index, EditOrderDescriptor editOrderDescriptor) {
        requireAllNonNull(index, editOrderDescriptor);

        this.index = index;
        this.editOrderDescriptor = new EditOrderDescriptor(editOrderDescriptor);
    }

    @Override
    public void undo(Model model) throws CommandException {
    }

    @Override
    public void redo(Model model) throws CommandException {
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Order> lastShownList = model.getFilteredOrderList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Message.MESSAGE_INVALID_INDEX);
        }

        Order orderToEdit = lastShownList.get(index.getZeroBased());
        Order editedOrder = createEditedOrder(orderToEdit, editOrderDescriptor);

        model.setOrder(orderToEdit, editedOrder);
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_ORDERS);
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, editedOrder),
                CommandResult.DisplayedPage.ORDER);
    }

    private static Order createEditedOrder(Order toEdit, EditOrderDescriptor editOrderDescriptor) {
        assert toEdit != null;


        Customer newCustomer = new Customer(
                editOrderDescriptor.getCustomerName().orElse(toEdit.getCustomer().name),
                editOrderDescriptor.getCustomerContact().orElse(toEdit.getCustomer().contact)
        );
        Date newDate = editOrderDescriptor.getDeliveryDate().orElse(toEdit.getDeliveryDate());
        Map<Product, Integer> newItems = editOrderDescriptor.getItems().orElse(toEdit.getItems());
        String newRemarks = editOrderDescriptor.getRemarks().orElse(toEdit.getRemarks());
        Order.Status newStatus = editOrderDescriptor.getStatus().orElse(toEdit.getStatus());
        return new Order(newCustomer, newDate, newStatus, newRemarks, newItems);
    }

//    private Order getOrder(BakingList bakingList) throws DukeException {
//        if (params.containsKey(("i"))) {
//            return getOrderByIndex(bakingList, params.get("i").get(0));
//        } else {
//            return getOrderById(bakingList, params.get("id").get(0));
//        }
//    }

//    private Order getOrderById(BakingList bakingList, String i) throws DukeException {
//        long id;
//        try {
//            id = Long.parseLong(params.get("id").get(0));
//        } catch (NumberFormatException e) {
//            throw new DukeException("Please provide a valid order ID");
//        }
//
//        for (Order order : bakingList.getOrderList()) {
//            if (order.getId() == id) {
//                return order;
//            }
//        }
//
//        throw new DukeException("Unknown ID");
//    }

//    private Order getOrderByIndex(BakingList bakingList, String i) throws DukeException {
//        int index;
//        try {
//            index = Integer.parseInt(params.get("i").get(0)) - 1;
//        } catch (NumberFormatException e) {
//            throw new DukeException("Please provide a valid index");
//        }
//
//        if (index < 0 || index >= bakingList.getOrderList().size()) {
//            throw new DukeException("Index out of bound.");
//        }
//
//        return bakingList.getOrderList().get(index);
//    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditOrderDescriptor {
        private String customerName;
        private String customerContact;
        private Date deliveryDate;
        private Map<Product, Integer> items;
        private String remarks;
        private Order.Status status;

        public EditOrderDescriptor() {
        }

        public EditOrderDescriptor(EditOrderDescriptor toCopy) {
            setCustomerName(toCopy.customerName);
            setCustomerContact(toCopy.customerContact);
            setDeliveryDate(toCopy.deliveryDate);
            setItems(toCopy.items);
            setRemarks(toCopy.remarks);
            setStatus(toCopy.status);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(customerName, customerContact, deliveryDate, items, remarks, status);
        }


        public void setCustomerName(String name) {
            this.customerName = name;
        }

        public void setCustomerContact(String contact) {
            this.customerContact = contact;
        }

        public void setDeliveryDate(Date deliveryDate) {
            this.deliveryDate = deliveryDate;
        }

        public void setItems(Map<Product, Integer> items) {
            this.items = (items != null) ? new HashMap<>(items) : null;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public void setStatus(Order.Status status) {
            this.status = status;
        }

        public Optional<String> getCustomerName() {
            return Optional.ofNullable(customerName);
        }

        public Optional<String> getCustomerContact() {
            return Optional.ofNullable(customerContact);
        }

        public Optional<Date> getDeliveryDate() {
            return Optional.ofNullable(deliveryDate);
        }

        public Optional<Map<Product, Integer>> getItems() {
            return Optional.ofNullable(items);
        }

        public Optional<String> getRemarks() {
            return Optional.ofNullable(remarks);
        }

        public Optional<Order.Status> getStatus() {
            return Optional.ofNullable(status);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            EditOrderDescriptor that = (EditOrderDescriptor) o;
            return Objects.equals(customerName, that.customerName) &&
                    Objects.equals(customerContact, that.customerContact) &&
                    Objects.equals(deliveryDate, that.deliveryDate) &&
                    Objects.equals(items, that.items) &&
                    Objects.equals(remarks, that.remarks) &&
                    status == that.status;
        }

        @Override
        public int hashCode() {
            return Objects.hash(customerName, customerContact, deliveryDate, items, remarks, status);
        }
    }
}
