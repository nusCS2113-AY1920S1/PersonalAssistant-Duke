package duke.ui;

import duke.DukeCore;
import duke.data.DukeObject;
import duke.exception.DukeFatalException;
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
            showErrorDialogAndShutdown("Fatal error encountered on application startup", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void print(String message) {
        String displayMessage = message.replace("\t", "\n");
        if (mainWindow != null) {
            mainWindow.print(displayMessage);
        }
    }

    @Override
    public void updateUi(String message) throws DukeFatalException {
        if (mainWindow != null) {
            mainWindow.updateUi(message);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showInfoDialog(String title, String message) {
        final Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
        infoAlert.setTitle(title);
        infoAlert.setHeaderText(message);
        infoAlert.showAndWait();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showErrorDialogAndShutdown(String title, Throwable e) {
        final Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle(title);
        errorAlert.setHeaderText(e.getMessage());
        errorAlert.setContentText(e.toString());
        errorAlert.showAndWait();

        Platform.exit();
        System.exit(1);
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
    private void showMainWindow(Stage primaryStage) throws DukeFatalException {
        mainWindow = new MainWindow(primaryStage, core);
        mainWindow.show();
    }
}
