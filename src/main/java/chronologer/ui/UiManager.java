package chronologer.ui;

import chronologer.ChronologerMain;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Manager of the UI component of the application.
 */
public class UiManager implements Ui {

    private static final Image GUI_ICON = new Image(ChronologerMain.class.getResourceAsStream("/images/GuiLogo.png"));
    private ChronologerMain main;
    private MainWindow mainWindow;

    /**
     * Constructor for the UIManager.
     *
     * @param main Holds the main of Chronologer which contains all essential features of the program.
     */
    public UiManager(ChronologerMain main) {
        this.main = main;
    }

    /**
     * Launches the primary stage of the application.
     */
    @Override
    public void start(Stage primaryStage) {
        setApplicationIcon(primaryStage);
        try {
            showMainWindow(primaryStage);
        } catch (Throwable e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Shows the main UI window of the application.
     *
     * @param baseStage The base stage for the application.
     */
    private void showMainWindow(Stage baseStage) {
        mainWindow = new MainWindow(baseStage, main);
        mainWindow.show();
    }

    /**
     * Prints a message.
     */
    @Override
    public void print(String message) {
        mainWindow.print(message);
    }

    /**
     * Sets the Chronologer application's icon.
     *
     * @param baseStage The base stage for the application.
     */
    private void setApplicationIcon(Stage baseStage) {
        baseStage.getIcons().add(GUI_ICON);
    }

}
