package duke.model.order;

import duke.logic.command.order.SortOrderCommand;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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
            return o1.getDeliveryDate().compareTo(o2.getDeliveryDate());

        } else if (SortOrderCommand.SortCriteria.STATUS.equals(criteria)) {
            return Integer.compare(statusSequence.indexOf(o1.getStatus()),
                statusSequence.indexOf(o2.getStatus()));

        } else if (SortOrderCommand.SortCriteria.TOTAL.equals(criteria)) {
            return Double.compare(o1.getTotal(), o2.getTotal());

        }

        return 0;
    }
}
