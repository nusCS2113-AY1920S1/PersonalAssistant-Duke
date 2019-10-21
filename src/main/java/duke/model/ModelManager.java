package duke.model;

import duke.commons.core.index.Index;
import duke.model.inventory.Ingredient;
import duke.model.commons.Item;
import duke.model.order.Order;
import duke.model.sale.Sale;
import duke.model.product.Product;
import duke.model.shortcut.Shortcut;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

import static duke.commons.util.CollectionUtil.requireAllNonNull;
import static java.util.Objects.requireNonNull;

/**
 * Represents the in-memory model of baking home data.
 */
public class ModelManager implements Model {
    private final VersionedBakingHome bakingHome;
    private final FilteredList<Order> filteredOrders;
    private final FilteredList<Sale> filteredSales;
    private final FilteredList<Product> filteredProducts;
    private final FilteredList<Item<Ingredient>> filteredInventory;
    private final FilteredList<Item<Ingredient>> filteredShoppingList;

    /**
     * Initializes a ModelManager with the given BakingHome.
     */
    public ModelManager(ReadOnlyBakingHome bakingHome) {
        super();
        this.bakingHome = new VersionedBakingHome(bakingHome);
        this.filteredOrders = new FilteredList<>(this.bakingHome.getOrderList());
        this.filteredSales = new FilteredList<>(this.bakingHome.getSaleList());
        this.filteredProducts = new FilteredList<>(this.bakingHome.getProductList());
        this.filteredInventory = new FilteredList<>(this.bakingHome.getInventoryList());
        this.filteredShoppingList = new FilteredList<>(this.bakingHome.getShoppingList());
    }

    public ModelManager() {
        this(new BakingHome());
    }

    @Override
    public void setBakingHome(ReadOnlyBakingHome bakingHome) {
        this.bakingHome.resetData(bakingHome);
    }

    @Override
    public ReadOnlyBakingHome getBakingHome() {
        return this.bakingHome;
    }

    @Override
    public boolean canUndo() {
        return bakingHome.canUndo();
    }

    @Override
    public boolean canRedo() {
        return bakingHome.canRedo();
    }

    @Override
    public String undo() {
        return bakingHome.undo();
    }

    @Override
    public String redo() {
        return bakingHome.redo();
    }

    @Override
    public void commit(String commitMessage) {
        bakingHome.commit(commitMessage);
    }

    @Override
    public void setVersionControl(Boolean isEnabled) {
        bakingHome.setVersionControl(isEnabled);
    }

    //================Order operations=================

    @Override
    public boolean hasOrder(Order order) {
        requireNonNull(order);
        return bakingHome.getOrderList().contains(order);
    }

    @Override
    public void deleteOrder(Order target) {
        bakingHome.removeOrder(target);
    }

    @Override
    public void addOrder(Order order) {
        bakingHome.addOrder(order);
        updateFilteredOrderList(PREDICATE_SHOW_ALL_ORDERS);
    }

    @Override
    public void setOrder(Order target, Order editedOrder) {
        requireNonNull(target);
        requireNonNull(editedOrder);

        bakingHome.setOrder(target, editedOrder);
    }

    @Override
    public void setOrder(Index index, Order order) {
        requireAllNonNull(index, order);

        bakingHome.setOrder(index, order);
    }

    @Override
    public ObservableList<Order> getFilteredOrderList() {
        return filteredOrders;
    }

    @Override
    public void updateFilteredOrderList(Predicate<Order> predicate) {
        requireNonNull(predicate);
        filteredOrders.setPredicate(predicate);
    }

    //================Sale operations=================


    @Override
    public void addSale(Sale sale) {
        bakingHome.addSale(sale);
        updateFilteredSaleList(PREDICATE_SHOW_ALL_SALES);
    }

    @Override
    public boolean hasSale(Sale sale) {
        requireNonNull(sale);
        return bakingHome.getSaleList().contains(sale);
    }

    @Override
    public void deleteSale(Sale target) {
        bakingHome.getOrderList().remove(target);
    }

    @Override
    public void setSale(Sale target, Sale editedSale) {
        requireNonNull(target);
        requireNonNull(editedSale);

        bakingHome.setSale(target, editedSale);
    }

    @Override
    public void setSale(Index index, Sale sale) {
        requireAllNonNull(index, sale);

        bakingHome.setSale(index, sale);
    }

    @Override
    public ObservableList<Sale> getFilteredSaleList() {
        return filteredSales;
    }

    @Override
    public void updateFilteredSaleList(Predicate<Sale> predicate) {
        requireNonNull(predicate);
        filteredSales.setPredicate(predicate);
    }

    @Override
    public void addSaleFromOrder(Order order) {
        String description = "Order " + order.getId() + " completed.";
        double value = order.getTotal();
        Date deliveryDate = order.getDeliveryDate();
        String remarks = order.getRemarks();
        bakingHome.addSale(new Sale(description, value, deliveryDate, remarks));
        updateFilteredSaleList(PREDICATE_SHOW_ALL_SALES);
    }

    @Override
    public void addSaleFromShopping(/*String purchaseDescription, double purchaseValue, Date purchaseDate, String purchaseRemarks*/) {
        /*
        String description = "Purchased ingredients!" + purchaseDescription;
        double value = purchaseValue;
        Date date = purchaseDate;
        String remarks = purchaseRemarks;
        bakingHome.addSale(new Sale(description, value, date, remarks));
        updateFilteredSaleList(PREDICATE_SHOW_ALL_SALES);
         */
    }

    //========Product operations==========
    @Override
    public void addProduct(Product product) {
        bakingHome.addProduct(product);
        updateFilteredProductList(PREDICATE_SHOW_ACTIVE_PRODUCTS);
    }

    /**
     * Replaces the given product {@code originalProduct} in the list with {@code editedProduct}. {@code
     * originalProduct} must exist in product list
     */
    @Override
    public void setProduct(Product originalProduct, Product editedProduct) {
        requireNonNull(originalProduct);
        requireNonNull(editedProduct);

        bakingHome.setProduct(originalProduct, editedProduct);
    }

    @Override
    public boolean hasProduct(Product product) {
        requireNonNull(product);
        return bakingHome.getProductList().contains(product);
    }

    /**
     * Returns an unmodifiable view of the filtered product list.
     */
    @Override
    public ObservableList<Product> getFilteredProductList() {
        return filteredProducts;
    }

    @Override
    public void updateFilteredProductList(Predicate<Product> predicate) {
        requireNonNull(predicate);
        filteredProducts.setPredicate(predicate);
    }

    //========Inventory operations==========

    @Override
    public void addInventory(Item<Ingredient> toAdd) {
        requireNonNull(toAdd);
        bakingHome.addInventory(toAdd);
        updateFilteredInventoryList(PREDICATE_SHOW_ALL_INVENTORY);
    }

    @Override
    public void updateFilteredInventoryList(Predicate<Item<Ingredient>> predicate) {
        requireNonNull(predicate);
        filteredInventory.setPredicate(predicate);
    }

    @Override
    public boolean hasInventory(Item<Ingredient> ingredientItem) {
        requireNonNull(ingredientItem);
        return bakingHome.getInventoryList().contains(ingredientItem);
    }

    @Override
    public void deleteInventory(Item<Ingredient> toDelete) {
        requireNonNull(toDelete);
        bakingHome.removeInventory(toDelete);
    }

    @Override
    public void setInventory(Item<Ingredient> toEdit, Item<Ingredient> edited) {
        requireAllNonNull(toEdit, edited);
        bakingHome.setInventory(toEdit, edited);
    }

    @Override
    public void setInventory(List<Item<Ingredient>> replacement) {
        requireNonNull(replacement);
        bakingHome.setInventory(replacement);
    }

    @Override
    public void clearInventory(List<Item<Ingredient>> emptyList) {
        bakingHome.clearInventory(emptyList);
    }

    @Override
    public boolean hasIngredient(Ingredient ingredient) {
        requireNonNull(ingredient);
        List<Item<Ingredient>> inventoryList = bakingHome.getInventoryList();
        for (Item<Ingredient> item : inventoryList) {
            if (item.getItem().equals(ingredient)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deductIngredient(Ingredient ingredient, double amount) {
        requireAllNonNull(ingredient, amount);
        return bakingHome.deductIngredient(ingredient, amount);
    }

    @Override
    public ObservableList<Item<Ingredient>> getFilteredInventoryList() {
        return filteredInventory;
    }

    //========Shopping List operations==========

    @Override
    public void addShoppingList(Item<Ingredient> toAdd) {
        requireNonNull(toAdd);
        bakingHome.addShoppingList(toAdd);
        updateFilteredShoppingList(PREDICATE_SHOW_ALL_SHOPPING);
    }

    @Override
    public void updateFilteredShoppingList(Predicate<Item<Ingredient>> predicate) {
        requireNonNull(predicate);
        filteredShoppingList.setPredicate(predicate);
    }

    @Override
    public boolean hasShoppingList(Item<Ingredient> ingredientItem) {
        requireNonNull(ingredientItem);
        return bakingHome.getShoppingList().contains(ingredientItem);
    }

    @Override
    public void deleteShoppingList(Item<Ingredient> toDelete) {
        requireNonNull(toDelete);
        bakingHome.removeShoppingList(toDelete);
    }

    @Override
    public void setShoppingList(Item<Ingredient> toEdit, Item<Ingredient> edited) {
        requireAllNonNull(toEdit, edited);
        bakingHome.setShoppingList(toEdit, edited);
    }

    @Override
    public void setShoppingList(List<Item<Ingredient>> replacement) {
        requireNonNull(replacement);
        bakingHome.setShoppingList(replacement);
    }

    @Override
    public void clearShoppingList(List<Item<Ingredient>> emptyList) {
        bakingHome.clearShoppingList(emptyList);
    }

    @Override
    public ObservableList<Item<Ingredient>> getFilteredShoppingList() {
        return filteredShoppingList;
    }

    //========Shortcut operations=========

    @Override
    public void setShortcut(Shortcut shortcut) {
        requireNonNull(shortcut);

        bakingHome.setShortcut(shortcut);
    }

    @Override
    public void removeShortcut(Shortcut shortcut) {
        requireNonNull(shortcut);
        bakingHome.removeShortcut(shortcut);
    }

    @Override
    public boolean hasShortcut(Shortcut shortcut) {
        requireNonNull(shortcut);
        return bakingHome.hasShortcut(shortcut);
    }

    @Override
    public List<Shortcut> getShortcutList() {
        return bakingHome.getShortcutList();
    }

}
