package duke.ui;

import duke.entities.Ingredient;
import duke.entities.Order;
import duke.entities.inventory.Inventory;

import java.util.LinkedHashMap;
import java.util.List;

public class Ui {

    private MainWindow mainWindow;

    public Ui(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    public void showMessage(String message) {
        mainWindow.showMessage(message);
    }

    public void showError(String message) {
        mainWindow.showErrorPopUp(message);
    }

    public void showOrderPage() {
        mainWindow.showOrderPage();
    }

    public void showRecipePage() {
        mainWindow.showRecipePage();
    }

    public void initializePages() {
        mainWindow.initializePages();
    }

    public void refreshOrderList(List<Order> orders, List<Order> all) {
        mainWindow.refreshOrderList(orders, all);
    }

/*    public void refreshRecipeList(RecipeList recipeList) {
        mainWindow.refreshRecipeList(recipeList);
    }*/
    public void disableInput() {
        mainWindow.disableInput();
    }

    public void refreshInventoryList(List<Inventory> inventory) {
        mainWindow.refreshInventoryList(inventory);
    }

    public void showInventoryPage() {
        mainWindow.showInventoryPage();
    }
}
