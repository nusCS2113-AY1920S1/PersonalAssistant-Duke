package duke.ui;

import duke.entities.Order;
import duke.entities.Sale;
import duke.entities.recipe.Recipe;

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

    public void showSalePage() {
        mainWindow.showSalePage();
    }

    public void initializePages() {
        mainWindow.initializePages();
    }

    public void refreshOrderList(List<Order> orders, List<Order> all) {
        mainWindow.refreshOrderList(orders, all);
    }

    public void refreshRecipeListPage(List<Recipe> recipeList) {
        mainWindow.refreshRecipeListPage(recipeList);
    }

    public void refreshSaleList(List<Sale> sale, List<Sale> all) {
        mainWindow.refreshSaleList(sale, all);
    }

    public void disableInput() {
        mainWindow.disableInput();
    }

}
