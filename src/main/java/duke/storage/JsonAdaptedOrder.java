package duke.storage;

import duke.model.order.Order;

import java.util.Date;

/**
 * Jackson-friendly version of {@link duke.model.order.Order}.
 */
public class JsonAdaptedOrder {

    private final long id;

    private final Date creationDate;
    private final String name;
    private final String contact;
    private final String remarks;
    private final Date deliveryDate;
    private final Order.Status status;
    private final double total;



}
