package duke.model;

import duke.commons.core.index.Index;
import duke.model.commons.Ingredient;
import duke.model.order.Order;
import duke.model.sale.Sale;
import duke.model.product.Product;
import duke.model.shortcut.Shortcut;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import java.util.List;
import java.util.function.Predicate;

import static duke.commons.util.CollectionUtil.requireAllNonNull;
import static java.util.Objects.requireNonNull;

/**
 * Represents the in-memory model of baking home data.
 */
public class ModelManager implements Model {
    private final BakingHome bakingHome;
    private final FilteredList<Order> filteredOrders;
    private final FilteredList<Product> filteredProducts;
    private final FilteredList<Ingredient> filteredInventory;

    /**
     * Initializes a ModelManager with the given BakingHome.
     */
    public ModelManager(ReadOnlyBakingHome bakingHome) {
        super();
        this.bakingHome = new BakingHome(bakingHome);
        this.filteredOrders = new FilteredList<>(this.bakingHome.getOrderList());
        this.filteredProducts = new FilteredList<>(this.bakingHome.getProductList());
        this.filteredInventory = new FilteredList<>(this.bakingHome.getInventoryList());
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

    //================Order operations=================

    @Override
    public boolean hasOrder(Order order) {
        requireNonNull(order);
        return bakingHome.getOrderList().contains(order);
    }

    @Override
    public void deleteOrder(Order target) {
        bakingHome.getOrderList().remove(target);
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

    @Override
    public boolean hasSale(Sale sale) {
        requireNonNull(sale);
        return bakingHome.getSaleList().contains(sale);
    }

    @Override
    public void deleteSale(Sale target) {
        bakingHome.getSaleList().remove(target);
    }

    @Override
    public void addSale(Sale sale) {
        bakingHome.addSale(sale);
    }

    @Override
    public void setSale(Sale target, Sale editedSale) {
        requireNonNull(target);
        requireNonNull(editedSale);

        bakingHome.setSale(target, editedSale);
    }

    //========comProduct operations==========
    @Override
    public void addProduct(Product product) {
        bakingHome.addProduct(product);
        updateFilteredProductList(PREDICATE_SHOW_ACTIVE_PRODUCTS);
    }

    /**
     * Replaces the given product {@code original} in the list with {@code editedProduct}. {@code
     * originalProduct} must exist in product list
     *
     * @param originalProduct
     * @param editedProduct
     */
    @Override
    public void setProduct(Product originalProduct, Product editedProduct) {
        requireNonNull(originalProduct);
        requireNonNull(editedProduct);

        bakingHome.setProduct(originalProduct, editedProduct);
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
    public void addInventory(Ingredient ingredient) {
        bakingHome.addInventory(ingredient);
        updateFilteredInventoryList(PREDICATE_SHOW_ALL_INVENTORY);
    }

    public void updateFilteredInventoryList(Predicate<Ingredient> predicate) {
        requireNonNull(predicate);
        filteredInventory.setPredicate(predicate);
    }

    @Override
    public ObservableList<Ingredient> getFilteredInventoryList() {
        return filteredInventory;
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
