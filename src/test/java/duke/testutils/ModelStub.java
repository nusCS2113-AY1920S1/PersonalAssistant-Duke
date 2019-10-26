package duke.testutils;

import duke.commons.core.index.Index;
import duke.model.BakingHome;
import duke.model.Model;
import duke.model.ReadOnlyBakingHome;
import duke.model.commons.Item;
import duke.model.inventory.Ingredient;
import duke.model.order.Order;
import duke.model.product.Product;
import duke.model.sale.Sale;
import duke.model.shortcut.Shortcut;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import java.util.List;
import java.util.function.Predicate;

/**
 * A stub for model class.
 */
public class ModelStub implements Model {
    private final BakingHome bakingHome;
    private final FilteredList<Order> filteredOrders;
    private final FilteredList<Sale> filteredSales;
    private final FilteredList<Product> filteredProducts;
    private final FilteredList<Item<Ingredient>> filteredInventory;
    private final FilteredList<Item<Ingredient>> filteredShoppingList;

    /**
     * Creates ModelStub
     */
    public ModelStub() {
        this.bakingHome = new BakingHome();
        this.filteredOrders = new FilteredList<>(this.bakingHome.getOrderList());
        this.filteredSales = new FilteredList<>(this.bakingHome.getSaleList());
        this.filteredProducts = new FilteredList<>(this.bakingHome.getProductList());
        this.filteredInventory = new FilteredList<>(this.bakingHome.getInventoryList());
        this.filteredShoppingList = new FilteredList<>(this.bakingHome.getShoppingList());
    }

    @Override
    public boolean canUndo() {
        return false;
    }

    @Override
    public boolean canRedo() {
        return false;
    }

    @Override
    public String undo() {
        return null;
    }

    @Override
    public String redo() {
        return null;
    }

    @Override
    public void commit(String commitMessage) {

    }

    @Override
    public void setVersionControl(Boolean isEnabled) {

    }

    @Override
    public ReadOnlyBakingHome getBakingHome() {
        return null;
    }

    @Override
    public void setBakingHome(ReadOnlyBakingHome bakingHome) {

    }

    @Override
    public boolean hasOrder(Order order) {
        return bakingHome.hasOrder(order);
    }

    @Override
    public void deleteOrder(Order target) {
        bakingHome.removeOrder(target);
    }

    @Override
    public void addOrder(Order order) {
        bakingHome.addOrder(order);
    }

    @Override
    public void setOrder(Order target, Order editedOrder) {
        bakingHome.setOrder(target, editedOrder);
    }

    @Override
    public void setOrder(Index index, Order order) {
        bakingHome.setOrder(index, order);
    }

    @Override
    public ObservableList<Order> getFilteredOrderList() {
        return filteredOrders;
    }

    @Override
    public void updateFilteredOrderList(Predicate<Order> predicate) {
    }

    @Override
    public void deleteProduct(Product product) {
        bakingHome.removeProduct(product);
    }

    @Override
    public void addProduct(Product product) {
        bakingHome.addProduct(product);
    }

    @Override
    public void setProduct(Product originalProduct, Product editedProduct) {
        bakingHome.setProduct(originalProduct, editedProduct);
    }

    @Override
    public boolean hasProduct(Product product) {
        return false;
    }

    @Override
    public ObservableList<Product> getFilteredProductList() {
        return bakingHome.getProductList();
    }

    @Override
    public void updateFilteredProductList(Predicate<Product> predicate) {

    }

    @Override
    public void addSale(Sale sale) {

    }

    @Override
    public void deleteSale(Sale target) {

    }

    @Override
    public boolean hasSale(Sale sale) {
        return false;
    }

    @Override
    public void setSale(Sale target, Sale editedSale) {

    }

    @Override
    public void setSale(Index index, Sale sale) {

    }

    @Override
    public ObservableList<Sale> getFilteredSaleList() {
        return null;
    }

    @Override
    public void updateFilteredSaleList(Predicate<Sale> predicate) {

    }

    @Override
    public void addSaleFromOrder(Order order) {

    }

    @Override
    public void addSaleFromShopping(Double totalCost) {

    }

    @Override
    public ObservableList<Item<Ingredient>> getFilteredInventoryList() {
        return null;
    }

    @Override
    public void updateFilteredInventoryList(Predicate<Item<Ingredient>> predicate) {

    }

    @Override
    public void addInventory(Item<Ingredient> inventory) {

    }

    @Override
    public boolean hasInventory(Item<Ingredient> inventory) {
        return false;
    }

    @Override
    public boolean hasIngredient(Ingredient ingredient) {
        return false;
    }

    @Override
    public boolean deductIngredient(Ingredient ingredient, double amount) {
        return false;
    }

    @Override
    public void deleteInventory(Item<Ingredient> inventory) {

    }

    @Override
    public void setInventory(Item<Ingredient> toEdit, Item<Ingredient> edited) {

    }

    @Override
    public void setInventory(List<Item<Ingredient>> replacement) {

    }

    @Override
    public void clearInventory(List<Item<Ingredient>> emptyList) {

    }

    @Override
    public ObservableList<Item<Ingredient>> getFilteredShoppingList() {
        return null;
    }

    @Override
    public void updateFilteredShoppingList(Predicate<Item<Ingredient>> predicate) {

    }

    @Override
    public void addShoppingList(Item<Ingredient> toAdd) {

    }

    @Override
    public boolean hasShoppingList(Item<Ingredient> ingredientItem) {
        return false;
    }

    @Override
    public void deleteShoppingList(Item<Ingredient> toDelete) {

    }

    @Override
    public void setShoppingList(Item<Ingredient> toEdit, Item<Ingredient> edited) {

    }

    @Override
    public void setShoppingList(List<Item<Ingredient>> replacement) {

    }

    @Override
    public void clearShoppingList(List<Item<Ingredient>> emptyList) {

    }

    @Override
    public void setShortcut(Shortcut shortcut) {

    }

    @Override
    public void removeShortcut(Shortcut shortcut) {

    }

    @Override
    public boolean hasShortcut(Shortcut shortcut) {
        return false;
    }

    @Override
    public List<Shortcut> getShortcutList() {
        return null;
    }
}
