package duke.ui;

import duke.MainWindow;

import static duke.common.Messages.MESSAGE_BYE;
import static duke.common.Messages.DIVIDER;

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

    public String showGoodbye() {
        return MESSAGE_BYE;
    }

    public void showMessage(String message) {
        mainWindow.showMessage(message);
    }

    public String showLine() {
        return DIVIDER;
    }

    public void showLoadingError() {
        mainWindow.handleLoadingError();
    }

    public void showListTask() {
        mainWindow.handleListTask();
    }

//    public void showError(String errorMessage) {
//        System.out.println(errorMessage);
//    }
}