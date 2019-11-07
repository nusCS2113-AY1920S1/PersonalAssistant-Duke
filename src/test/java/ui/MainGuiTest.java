package ui;

import javafx.application.Application;
import ui.gui.MainGui;
import ui.gui.MainWindow;

public class MainGuiTest extends MainGui {
    private String taskPath = "savedTask.txt";
    private String walletPath = "savedWallet.txt";
    private MainWindow mainWindowController;

    /**
     * Tests the working of the GUI.
     * @param taskPath is the path the leads to the task content
     * @param walletPath is the path that leads to the wallet contents
     */
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
