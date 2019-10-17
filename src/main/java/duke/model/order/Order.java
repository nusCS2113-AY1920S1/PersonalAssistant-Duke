package duke.model.order;

import duke.model.commons.Item;
import duke.model.inventory.Ingredient;
import duke.model.product.Product;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import static duke.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents an order in order list.
 */
public class Order {

    //Identity field
    private final long id;

    //Data fields
    private final Customer customer;
    private final Date deliveryDate;
    private final Set<Item<Product>> items;
    private final String remarks;
    private final Status status;
    private final double total;

    private BooleanProperty isIngredientEnough;

    /**
     * Creates an order.
     * Every field must be present and not null.
     */
    public Order(Customer customer, Date deliveryDate, Status status,
                 String remarks, Set<Item<Product>> items, double total,
                 ObservableList<Item<Ingredient>> inventory) {
        requireAllNonNull(customer, deliveryDate, status, remarks, items, total);

        this.customer = customer;
        this.deliveryDate = deliveryDate;
        this.status = status;
        this.remarks = remarks;
        this.id = System.currentTimeMillis();
        this.items = items;
        this.total = total;

        this.isIngredientEnough = new SimpleBooleanProperty();

        updateIsIngredientEnough(inventory);

        inventory.addListener((ListChangeListener<Item<Ingredient>>) c -> updateIsIngredientEnough(inventory));
    }

    public enum Status {
        ACTIVE,
        COMPLETED,
        CANCELED
    }

    public Customer getCustomer() {
        return customer;
    }

    public long getId() {
        return id;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public Set<Item<Product>> getItems() {
        return Collections.unmodifiableSet(items);
    }

    public String getRemarks() {
        return remarks;
    }

    public Status getStatus() {
        return status;
    }

    public double getTotal() {
        return total;
    }

    public boolean isIsIngredientEnough() {
        return isIngredientEnough.get();
    }

    public BooleanProperty isIngredientEnoughProperty() {
        return isIngredientEnough;
    }

    private void updateIsIngredientEnough(ObservableList<Item<Ingredient>> inventory) {
        requireAllNonNull(inventory);

        Map<Ingredient, Double> requiredIngredientAmount = new HashMap<>();
        for (Item<Product> productItem : items) {
            for (Item<Ingredient> ingredientItem : productItem.getItem().getIngredients()) {
                if (requiredIngredientAmount.containsKey(ingredientItem.getItem())) {
                    requiredIngredientAmount.put(ingredientItem.getItem(),
                        requiredIngredientAmount.get(ingredientItem.getItem())
                            + ingredientItem.getQuantity().getNumber() * productItem.getQuantity().getNumber()
                    );
                } else {
                    requiredIngredientAmount.put(ingredientItem.getItem(),
                        ingredientItem.getQuantity().getNumber() * productItem.getQuantity().getNumber());
                }
            }
        }

        //TODO: REMOVE IO codes
        isIngredientEnough.setValue(true);
        for (Map.Entry<Ingredient, Double> entry : requiredIngredientAmount.entrySet()) {
            //System.out.println("I need " + entry.getKey().name + " " + entry.getValue().toString());
            boolean isFound = false;
            for (Item<Ingredient> ingredientItem : inventory) {
                //System.out.println("Inv: "+ ingredientItem.getItem().name + " " + ingredientItem.getQuantity().getNumber());
                if (ingredientItem.getItem().equals(entry.getKey())) {
                    //System.out.println("222");
                    isFound = true;
                    if (entry.getValue() > ingredientItem.getQuantity().getNumber()) {
                        //System.out.println("111");
                        isIngredientEnough.setValue(false);
                        break;
                    }
                }
            }
            if (!isFound) {
                isIngredientEnough.setValue(false);
            }
        }
        //System.out.println("Hi, " + isIngredientEnough);
    }

    @Override
    public String toString() {
        return String.format("ID: %s Customer: [%s] Date: %s Status: %s Items: %s",
                id, customer, deliveryDate, status, items);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Order order = (Order) o;
        return id == order.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
