package duke.ui;

import static duke.common.Messages.*;

public class Ui {

    private MainWindow mainWindow;

    public Ui(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    /**
     * Display welcome message of the program.
     */
    public String showWelcome() {
        return DIVIDER + "     Hello! I'm Duke\n" + "     What can I do for you?\n" + DIVIDER;
    }

    public void showLoadingError() {
        mainWindow.handleLoadingError();
    }
    public void showIngredientLoadingError() {
        System.out.println(ERROR_MESSAGE_LOADING + filePathInventory);
    }
}