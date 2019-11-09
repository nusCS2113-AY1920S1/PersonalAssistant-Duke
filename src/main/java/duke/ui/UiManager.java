package duke.ui;

import duke.DukeCore;
import duke.data.DukeObject;
import duke.exception.DukeFatalException;
import duke.ui.commons.UiStrings;
import duke.ui.window.MainWindow;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.List;

/**
 * Class that manages the UI module of Dr. Duke.
 */
public class UiManager implements Ui {
    // TODO: Move to UiUtils class.
    private static final Image ICON_APPLICATION = new Image(DukeCore.class.getResourceAsStream("/images/icon.png"));

    private DukeCore core;
    private MainWindow mainWindow;

    /**
     * Constructs the manager to manage the UI module.
     */
    public UiManager(DukeCore core) {
        // TODO: We do not need the entire core in here.
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
        } catch (Throwable t) {
            showErrorDialogAndShutdown(UiStrings.MESSAGE_ERROR_LAUNCH, t);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stop() {
        mainWindow.showMessage(UiStrings.MESSAGE_GOODBYE);

        PauseTransition pause = new PauseTransition(new Duration(1500));
        pause.setOnFinished(event -> Platform.exit());
        pause.play();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showMessage(String message) {
        final String displayMessage = message.replace("\t", "\n");

        // TODO: temporary workaround for JUnit tests
        if (mainWindow != null) {
            mainWindow.showMessage(displayMessage);
        }
    }

    /**
     * {@inheritDoc}
     */
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
    public void showErrorDialogAndShutdown(String errorTitle, Throwable error) {
        final Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle(errorTitle);
        errorAlert.setHeaderText(error.getMessage());
        errorAlert.setContentText(error.toString());
        errorAlert.showAndWait();

        Platform.exit();
    }

    /**
     * Sets the application's icon.
     *
     * @param primaryStage Main stage for the application.
     */
    private void setApplicationIcon(Stage primaryStage) {
        primaryStage.getIcons().add(ICON_APPLICATION);
    }

    /**
     * Initialises and displays main UI window, {@link MainWindow}, of the application.
     *
     * @param primaryStage Main stage for the application.
     */
    private void showMainWindow(Stage primaryStage) throws DukeFatalException {
        mainWindow = new MainWindow(primaryStage, core);
        mainWindow.show();
    }

    /* TODO: TEMPORARY */
    /**
     * {@inheritDoc}
     */
    @Override
    public List<DukeObject> getIndexedList(String type) {
        return mainWindow.getIndexedList(type);
    }
}
