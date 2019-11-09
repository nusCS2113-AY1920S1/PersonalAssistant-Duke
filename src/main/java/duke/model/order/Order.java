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
import java.util.concurrent.atomic.AtomicBoolean;

import static duke.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents an order in order list.
 */
public class Order {
    private BooleanProperty isIngredientEnough;

    //Identity field
    private final OrderId id;
    private final Remark remarks;

    //Data fields
    private final Date creationDate;
    private final Customer customer;
    private final Date deliveryDate;
    private final Set<Item<Product>> items;
    private final TotalPrice total;
    private final Status status;

    /**
     * Creates an {@code Order}.
     * Every field must be present and not null.
     *
     * @param customer of the order.
     * @param deliveryDate date of delivery. Can be
     * @param status Status of an order. see {@link Order.Status}.
     * @param remarks additional notes regarding the order.
     * @param items Products ordered in the order.
     * @param total total price of the order.
     */
    public Order(Customer customer,
                 Date deliveryDate,
                 Status status,
                 Remark remarks,
                 Set<Item<Product>> items,
                 TotalPrice total,
                 OrderId id,
                 Date creationDate) {
        requireAllNonNull(customer, deliveryDate, status, remarks, items, total, id);

        this.id = id;
        this.creationDate = creationDate;

        this.customer = customer;
        this.deliveryDate = deliveryDate;
        this.status = status;
        this.remarks = remarks;
        this.items = items;
        this.total = total;

        this.isIngredientEnough = new SimpleBooleanProperty();
    }

    /**
     * Creates an {@code Order}.
     * Every field must be present and not null.
     * Order id and creation date are generated based on current system time.
     */
    public Order(Customer customer,
                 Date deliveryDate,
                 Status status,
                 Remark remarks,
                 Set<Item<Product>> items,
                 TotalPrice total) {
        this(customer, deliveryDate, status, remarks, items, total, OrderId.getOrderId(), generateCreationDate());
    }

    private static Date generateCreationDate() {
        return new Date(System.currentTimeMillis());
    }

    public OrderId getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Remark getRemarks() {
        return remarks;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public Set<Item<Product>> getItems() {
        return Collections.unmodifiableSet(items);
    }

    public TotalPrice getTotal() {
        return total;
    }

    public Status getStatus() {
        return status;
    }

    public boolean isIngredientEnough() {
        return isIngredientEnough.get();
    }

    public BooleanProperty isIngredientEnoughProperty() {
        return isIngredientEnough;
    }

    /**
     * Makes the order's {@code isIngredientEnough} property changes dynamically with the change of {@code inventory}.
     */
    public void listenToInventory(ObservableList<Item<Ingredient>> inventory) {
        requireAllNonNull(inventory);

        updateIsIngredientEnough(inventory);
        inventory.addListener((ListChangeListener<Item<Ingredient>>) c -> updateIsIngredientEnough(inventory));
    }

    /**
     * Updates the {@code isIngredientEnough} property based on {@code inventory}.
     */
    private void updateIsIngredientEnough(ObservableList<Item<Ingredient>> inventory) {
        requireAllNonNull(inventory);

        isIngredientEnough.setValue(
            isRequiredIngredientSufficient(inventory, getRequiredIngredients(inventory))
        );
    }

    /**
     * Status of an order.
     */
    public enum Status {
        ACTIVE,
        COMPLETED,
        CANCELED
    }

    private Map<Ingredient, Double> getRequiredIngredients(ObservableList<Item<Ingredient>> inventory) {
        requireAllNonNull(inventory);

        //Key: the ingredient needed;
        //Value: Amount needed for that ingredient
        Map<Ingredient, Double> requiredIngredients = new HashMap<>();

        //Iterate through all items in the order and compute ingredients needed
        for (Item<Product> productItem : items) {
            double numberOfCopies = productItem.getQuantity().getNumber();
            for (Item<Ingredient> ingredientItem : productItem.getItem().getIngredients()) {
                Ingredient ingredientNeeded = ingredientItem.getItem();
                Double amountNeeded = ingredientItem.getQuantity().getNumber();

                //The amount of an ingredient required by an order item =
                //the amount of that ingredient required to make one copy of that item * number of copies in the order.
                //For example, if 2 units of milk are needed to make one cheese cake,
                // and the order contains 5 cheese cakes,
                //then milk needed for cheese cake = 2 * 5 = 10.
                requiredIngredients.computeIfPresent(ingredientNeeded, (k, v) -> v + amountNeeded * numberOfCopies);
                requiredIngredients.putIfAbsent(ingredientNeeded, amountNeeded * numberOfCopies);
            }
        }

        return requiredIngredients;
    }

    private boolean isRequiredIngredientSufficient(ObservableList<Item<Ingredient>> inventory,
                                                   Map<Ingredient, Double> requiredIngredients) {
        requireAllNonNull(inventory, requiredIngredients);

        AtomicBoolean isEnough = new AtomicBoolean(true);

        //Iterate through all ingredients needed.
        requiredIngredients.forEach((requiredIngredient, requiredAmount) -> {
            boolean isFound = false;
            //Iterate through inventory to find the required ingredient.
            for (Item<Ingredient> ingredientItem : inventory) {
                Ingredient inventoryIngredient = ingredientItem.getItem();
                double inventoryAmount = ingredientItem.getQuantity().getNumber();
                if (requiredIngredient.equals(inventoryIngredient)) {
                    isFound = true;
                    if (requiredAmount > inventoryAmount) {
                        isEnough.set(false);
                        break;
                    }
                }
            }

            //If ingredient needed is not in inventory
            if (!isFound) {
                isEnough.set(false);
            }
        });

        return isEnough.get();
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
