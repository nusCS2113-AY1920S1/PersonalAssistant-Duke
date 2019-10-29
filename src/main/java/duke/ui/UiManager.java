package duke.ui;

import duke.DukeCore;
import duke.data.DukeObject;
import duke.ui.window.MainWindow;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.List;

/**
 * Manager of the UI component of the application.
 */
public class UiManager implements Ui {
    private static final Image ICON_APPLICATION = new Image(DukeCore.class.getResourceAsStream("/images/icon.png"));

    private MainWindow mainWindow;
    private DukeCore core;

    /**
     * Constructs the UIManager.
     *
     * @param core Core of Dr. Duke.
     */
    public UiManager(DukeCore core) {
        // TODO: We do not need the entire Duke's core in the UI component.
        this.core = core;
    }

    /**
     * {@inheritDoc}
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
     * {@inheritDoc}
     */
    @Override
    public void print(String message) {
        String displayMessage = message.replace("\t", "\n");
        mainWindow.print(displayMessage);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DukeObject> getIndexedList(String type) {
        return mainWindow.getIndexedList(type);
    }

    /**
     * Set application's icon.
     *
     * @param primaryStage Main stage for the application.
     */
    private void setApplicationIcon(Stage primaryStage) {
        primaryStage.getIcons().add(ICON_APPLICATION);
    }

    /**
     * Show main UI window of the application.
     *
     * @param primaryStage Main stage for the application.
     */
    private void showMainWindow(Stage primaryStage) {
        mainWindow = new MainWindow(primaryStage, core);
        mainWindow.show();
    }

    /**
     * Shows an error alert dialog with {@code title} and error message, {@code e}.
     * Exits the application after the user has closed the alert dialog.
     *
     * @param title Title of error dialog.
     * @param error Error.
     */
    private void showErrorDialogAndShutdown(String title, Throwable error) {
        final Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.initOwner(mainWindow.getPrimaryStage());
        errorAlert.setTitle(title);
        errorAlert.setHeaderText(error.getMessage());
        errorAlert.setContentText(error.toString());
        errorAlert.showAndWait();

        Platform.exit();
        System.exit(1);
    }
}
