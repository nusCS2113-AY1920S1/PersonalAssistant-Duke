package ui;

import javafx.application.Application;
import ui.gui.MainGui;
import ui.gui.MainWindow;

public class MainGuiTest extends MainGui {
    private String taskPath = "savedTask.txt";
    private String walletPath = "savedWallet.txt";
    private MainWindow mainWindowController;

    public void initialize(String taskPath, String walletPath) {
        String[] args = {};
        this.taskPath = taskPath;
        this.walletPath = walletPath;
        Application.launch(MainGui.class);
    }

    public MainWindow getMainWindowController() {
        return mainWindowController;
    }
}
