package duke.ui;

import duke.order.Order;

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

    public void showOrderPane() {
        mainWindow.showOrderPane();
    }

    public void refreshOrderList(List<Order> orders, List<Order> all) {
        mainWindow.refreshOrderList(orders, all);
    }

    public void disableInput() {
        mainWindow.disableInput();
    }

//    public void showPopUp(String message, boolean isDisappearAfterInput) {
//        mainWindow.showPopUp(message, isDisappearAfterInput);
//    }
}
