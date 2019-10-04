package duke.model.order;

import duke.model.commons.Customer;
import duke.model.commons.Product;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static duke.commons.util.CollectionUtil.requireAllNonNull;

public class Order {
    public static enum Status {
        ACTIVE,
        COMPLETED,
        CANCELED
    }

    private Customer customer;
    private final long id;
    private Date deliveryDate;
    private Map<Product, Integer> items = new HashMap<>();
    private String remarks;
    private Status status;

    public Order() {
        this.customer = new Customer("N/A", "N/A");
        this.deliveryDate = Calendar.getInstance().getTime();
        ;
        this.status = Status.ACTIVE;
        this.remarks = "N/A";
        this.id = System.currentTimeMillis();
    }

    public Order(Customer customer, Date deliveryDate, Status status, String remarks, Product... products) {
        requireAllNonNull(customer, deliveryDate, status, remarks, products);

        this.customer = customer;
        this.deliveryDate = deliveryDate;
        this.status = status;
        this.remarks = remarks;
        this.id = System.currentTimeMillis();
        for (Product product : products) {
            if (items.containsKey(product)) {
                items.put(product, 1);
            } else {
                items.put(product, items.get(product) + 1);
            }
        }
    }


}
