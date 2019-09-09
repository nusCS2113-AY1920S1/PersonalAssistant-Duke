package duke.ui;

import duke.task.TaskList;

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

    public void refreshTaskList(TaskList tasks, TaskList all) {
        mainWindow.refreshTaskList(tasks, all);
    }

    public void disableInput() {
        mainWindow.disableInput();
    }

//    public void showPopUp(String message, boolean isDisappearAfterInput) {
//        mainWindow.showPopUp(message, isDisappearAfterInput);
//    }
}
