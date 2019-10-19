package duke.model.order;

import duke.model.commons.Item;
import duke.model.inventory.Ingredient;
import duke.model.product.Product;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.util.*;
import static duke.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents an order in order list.
 */
public class Order {

    //Identity field
    private final long id;

    //Data fields
    private final Date creationDate;
    private final Customer customer;
    private final Date deliveryDate;
    private final Set<Item<Product>> items;
    private final String remarks;
    private final Status status;
    private final double total;

    private BooleanProperty isIngredientEnough = new SimpleBooleanProperty();

    /**
     * Creates an order.
     * Every field must be present and not null.
     */
    public Order(Customer customer, Date deliveryDate, Status status,
                 String remarks, Set<Item<Product>> items, double total) {
        requireAllNonNull(customer, deliveryDate, status, remarks, items, total);

        this.id = generateId();
        this.creationDate = generateCreationDate();

        this.customer = customer;
        this.deliveryDate = deliveryDate;
        this.status = status;
        this.remarks = remarks;
        this.items = items;
        this.total = total;
    }

    /**
     * Creates an order.
     * Every field must be present and not null.
     */
    public Order(Customer customer, Date deliveryDate, Status status,
                 String remarks, Set<Item<Product>> items, double total,
                 Long id, Date creationDate) {
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

    public void listenToInventory(ObservableList<Item<Ingredient>> inventory) {
        updateIsIngredientEnough(inventory);
        System.out.println("updating");
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

    public Date getCreationDate() {
        return creationDate;
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


    private long generateId() {
        return System.currentTimeMillis();
    }


    private Date generateCreationDate() {
        return Calendar.getInstance().getTime();
    }


    /**
     * Updates the {@code isIngredientEnough} property based on {@code inventory}.
     */
    //TODO: Remove IO
    private void updateIsIngredientEnough(ObservableList<Item<Ingredient>> inventory) {
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

//        System.out.println("required");
//        requiredIngredients.forEach((k,v) ->
//        {
//            System.out.println(k.name + " " + v);
//        });
//        System.out.println("have");
//        for (Item<Ingredient> ingredientItem : inventory) {
//            Ingredient inventoryIngredient = ingredientItem.getItem();
//            double inventoryAmount = ingredientItem.getQuantity().getNumber();
//            System.out.println(ingredientItem.getItem().name + " " + inventoryAmount);
//        }

        isIngredientEnough.setValue(true);

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
                        isIngredientEnough.setValue(false);
                        break;
                    }
                }
            }

            //If ingredient needed is not in inventory
            if (!isFound) {
                isIngredientEnough.setValue(false);
            }
        });

//        System.out.println(isIngredientEnough);
//        System.out.println("---");
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
