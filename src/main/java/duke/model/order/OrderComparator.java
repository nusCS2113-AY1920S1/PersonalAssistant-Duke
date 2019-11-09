package duke.model.order;

import duke.logic.command.order.SortOrderCommand;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static duke.model.order.Order.Status;

/**
 * A comparator for {@code Order} that supports comparing by status, total price,
 * deadline or creation date.
 */
public class OrderComparator implements Comparator<Order> {
    private static final List<Order.Status> statusSequence = new ArrayList<>();
    private final SortOrderCommand.SortCriteria criteria;

    /**
     * Creates a {@code OrderComparator}.
     *
     * @param criteria to compare
     */
    public OrderComparator(SortOrderCommand.SortCriteria criteria) {
        assert (criteria != null);

        this.criteria = criteria;

        statusSequence.add(Order.Status.ACTIVE);
        statusSequence.add(Order.Status.COMPLETED);
        statusSequence.add(Order.Status.CANCELED);
    }

    @Override
    public int compare(Order o1, Order o2) {
        if (SortOrderCommand.SortCriteria.CREATION.equals(criteria)) {
            return o1.getCreationDate().compareTo(o2.getCreationDate());

        } else if (SortOrderCommand.SortCriteria.DEADLINE.equals(criteria)) {
            return compareDeadLine(o1, o2);


        } else if (SortOrderCommand.SortCriteria.STATUS.equals(criteria)) {
            return compareStatus(o1, o2);

        } else if (SortOrderCommand.SortCriteria.TOTAL.equals(criteria)) {
            return compareTotal(o1, o2);
        }

        return 0;
    }

    private int compareDeadLine(Order o1, Order o2) {
        if (Status.ACTIVE.equals(o1.getStatus()) && !Status.ACTIVE.equals(o2.getStatus())) {
            return -1;
        }

        if (Status.ACTIVE.equals(o2.getStatus()) && !Status.ACTIVE.equals(o1.getStatus())) {
            return 1;
        }

        if (o1.getDeliveryDate().equals(o2.getDeliveryDate())) {
            return 0;
        } else if (o1.getDeliveryDate().before(o2.getDeliveryDate())) {
            return -1;
        } else {
            return 1;
        }
    }

    private int compareStatus(Order o1, Order o2) {
        return Integer.compare(statusSequence.indexOf(o1.getStatus()),
            statusSequence.indexOf(o2.getStatus()));
    }

    private int compareTotal(Order o1, Order o2) {
        return Double.compare(o1.getTotal(), o2.getTotal());
    }
}
