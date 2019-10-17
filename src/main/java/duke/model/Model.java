package duke.model;

import duke.commons.core.index.Index;
import duke.model.inventory.Ingredient;
import duke.model.commons.Item;
import duke.model.order.Order;
import duke.model.product.Product;
import duke.model.sale.Sale;
import duke.model.shortcut.Shortcut;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.function.Predicate;

/**
 * The API of the Model component.
 */
public interface Model {
    Predicate<Order> PREDICATE_SHOW_ALL_ORDERS = unused -> true;

    Predicate<Sale> PREDICATE_SHOW_ALL_SALES = unused -> true;

    Predicate<Product> PREDICATE_SHOW_ALL_PRODUCTS = product -> true;
    Predicate<Product> PREDICATE_SHOW_ACTIVE_PRODUCTS = product -> {
        return product.getStatus() == Product.Status.ACTIVE;
    };
    Predicate<Product> PREDICATE_SHOW_ARCHIVE_PRODUCTS = product -> {
        return product.getStatus() == Product.Status.ARCHIVE;
    };

    Predicate<Item<Ingredient>> PREDICATE_SHOW_ALL_INVENTORY = unused -> true;

    Predicate<Item<Ingredient>> PREDICATE_SHOW_ALL_SHOPPING = unused -> true;

    /**
     * Returns true if the model has previous baking home states to restore.
     */
    boolean canUndo();

    /**
     * Returns true if the model has undone baking home states to restore.
     */
    boolean canRedo();

    /**
     * Restores BakingHome to its previous state.
     * @return the commit message of the current state.
     */
    String undo();

    /**
     * Restores the address book to its previously undone state.
     * @return the commit message of the previous state.
     */
    String redo();

    /**
     * Saves the current baking home state for undo/redo.
     *
     * @param commitMessage the message describing the details of the commit
     */
    void commit(String commitMessage);

    /**
     * TODO: add details.
     *
     * @param isEnabled should be set to true to enable version control.
     */
    void setVersionControl(Boolean isEnabled);

    /**
     * Returns the BakingHome.
     */
    ReadOnlyBakingHome getBakingHome();

    /**
     * Replaces baking home data with the data in {@code bakingHome}.
     */
    void setBakingHome(ReadOnlyBakingHome bakingHome);

    //========Order operations========

    /**
     * Returns true if an order with the same id as {@code order} exists in order list.
     */
    boolean hasOrder(Order order);

    /**
     * Deletes the given order.
     * The order must exist in order list.
     */
    void deleteOrder(Order target);

    /**
     * Adds the given order.
     * The order must not exist in order list
     */
    void addOrder(Order order);

    /**
     * Replaces the given order {@code target} in the list with {@code editedOrder}.
     * {@code target} must exist in order list
     */
    void setOrder(Order target, Order editedOrder);

    /**
     * Replaces the order at {@code Index} in the list with {@code editedOrder}.
     * {@code Index} must be a valid index
     * {@code target} must exist in order list
     */
    void setOrder(Index index, Order order);

    /**
     * Returns an unmodifiable view of the filtered order list.
     */
    ObservableList<Order> getFilteredOrderList();

    /**
     * Updates the filter of the filtered order list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredOrderList(Predicate<Order> predicate);

    //========Product operations=========

    /**
     * Adds the given product.
     */
    void addProduct(Product product);

    /**
     * Replaces the given product {@code original} in the list with {@code editedProduct}.
     * {@code originalProduct} must exist in product list
     */
    void setProduct(Product originalProduct, Product editedProduct);

    /**
     * Returns true if an product has the same name as {@code product} exists in product list.
     */
    boolean hasProduct(Product product);

    /**
     * Returns an unmodifiable view of the filtered product list.
     */
    ObservableList<Product> getFilteredProductList(); //implement archive

    void updateFilteredProductList(Predicate<Product> predicate);

    //========Sale operations=========
    /**
     * Adds the given sale.
     * The sale must not exist in sale list
     */
    void addSale(Sale sale);

    /**
     * Deletes the given sale.
     * The sale must exist in order list.
     */
    void deleteSale(Sale target);

    /**
     * Returns true if an sale with the same id as {@code order} exists in sale list.
     */
    boolean hasSale(Sale sale);

    /**
     * Replaces the given sale {@code target} in the list with {@code editedSale}.
     * {@code target} must exist in order list
     */
    void setSale(Sale target, Sale editedSale);

    /**
     * Replaces the sale at {@code Index} in the list with {@code editedSale}.
     * {@code Index} must be a valid index
     * {@code target} must exist in order list
     */
    void setSale(Index index, Sale sale);

    /**
     * Returns an unmodifiable view of the filtered sale list.
     */
    ObservableList<Sale> getFilteredSaleList();

    void updateFilteredSaleList(Predicate<Sale> predicate);

    //========Ingredient operations======
    /**
     * Returns an unmodifiable view of the filtered inventory list.
     */
    ObservableList<Item<Ingredient>> getFilteredInventoryList();

    void updateFilteredInventoryList(Predicate<Item<Ingredient>> predicate);

    /**
     * Adds an inventory item to the inventory list
     * @param inventory The inventory item
     */
    void addInventory(Item<Ingredient> inventory);

    /**
     * Checks if the item with the same name already exists in the inventory list
     * @return true if the given inventory item already exists in the inventory list
     */
    boolean hasInventory(Item<Ingredient> inventory);

    boolean hasIngredient(Ingredient ingredient);

    boolean deductIngredient(Ingredient ingredient, double amount);

    /**
     * Removes an inventory item from the inventory list
     * @param inventory The inventory item
     */
    void deleteInventory(Item<Ingredient> inventory);

    /**
     * Edits the ingredient toEdit with the ingredient edited
     * @param toEdit the ingredient to be edited
     * @param edited the edited ingredient with changes
     */
    void setInventory(Item<Ingredient> toEdit, Item<Ingredient> edited);

    void setInventory(List<Item<Ingredient>> replacement);

    /**
     * Clears the inventory list
     * @param emptyList
     */
    void clearInventory(List<Item<Ingredient>> emptyList);

    //======Shopping list operations=====

    ObservableList<Item<Ingredient>> getFilteredShoppingList();

    void updateFilteredShoppingList(Predicate<Item<Ingredient>> predicate);

    /**
     * Adds an ingredient to the shopping list
     * @param toAdd the ingredient to be added
     */
    void addShoppingList(Item<Ingredient> toAdd);

    /**
     * Checks if the item with the same name already exists in the shopping list
     * @return true if the given ingredient item already exists in the shopping list
     */
    boolean hasShoppingList(Item<Ingredient> ingredientItem);

    /**
     * Removes an ingredient item from the shopping list
     * @param toDelete the ingredient item
     */
    void deleteShoppingList(Item<Ingredient> toDelete);

    /**
     * Edits the ingredient toEdit with the ingredient edited
     * @param toEdit the ingredient to be edited
     * @param edited the edited ingredient with changes
     */
    void setShoppingList(Item<Ingredient> toEdit, Item<Ingredient> edited);

    void setShoppingList(List<Item<Ingredient>> replacement);

    /**
     * Clears the shopping list
     * @param emptyList
     */
    void clearShoppingList(List<Item<Ingredient>> emptyList);

    //=========Shortcut operations=======

    /**
     * Adds {@code shortcut} to shortcut list.
     * If the shortcut already exists, it overrides the old shortcut.
     */
    void setShortcut(Shortcut shortcut);

    /**
     * Deletes the given {@code shortcut}.
     * The shortcut must exist in order list.
     */
    void removeShortcut(Shortcut shortcut);

    /**
     * Returns true if a shortcut with the same name as {@code order} exists in shortcut list.
     */
    boolean hasShortcut(Shortcut shortcut);

    /**
     * Returns an unmodifiable view of the shortcut list.
     */
    List<Shortcut> getShortcutList();
}
