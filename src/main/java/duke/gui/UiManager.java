package duke.gui;

import duke.DukeCore;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Manager of the UI component.
 */
public class UiManager implements Ui {
    private static final String ALERT_DIALOG_PANE_FIELD_ID = "ALERT_DIALOG";
    private static final String ICON_APPLICATION = "/images/icon.png";

    private MainWindow mainWindow;
    private DukeCore core;

    /**
     * Construct the UIManager.
     *
     * @param core DukeCore.
     */
    public UiManager(DukeCore core) {
        super();
        this.core = core;
    }

    /**
     * Shows an alert dialog on {@code owner} with the given parameters.
     * This method only returns after the user has closed the alert dialog.
     */
    private static void showAlertDialogAndWait(Stage owner, Alert.AlertType type, String title, String headerText,
                                               String contentText) {
        final Alert alert = new Alert(type);
        alert.initOwner(owner);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.getDialogPane().setId(ALERT_DIALOG_PANE_FIELD_ID);
        alert.showAndWait();
    }

    @Override
    public void start(Stage stage) {
        stage.getIcons().add(new Image(DukeCore.class.getResourceAsStream(UiManager.ICON_APPLICATION)));

        try {
            mainWindow = new MainWindow(stage, core);
            mainWindow.placeChildViews();
            mainWindow.show();
        } catch (Throwable e) {
            showFatalErrorDialogAndShutdown("Fatal error during initialisation", e);
        }
    }

    @Override
    public void print(String output) {
        mainWindow.print(output);
    }

    private void showAlertDialogAndWait(Alert.AlertType type, String title, String headerText, String contentText) {
        showAlertDialogAndWait(mainWindow.getPrimaryStage(), type, title, headerText, contentText);
    }

    /**
     * Shows an error alert dialog with {@code title} and error message, {@code e},
     * and exits the application after the user has closed the alert dialog.
     */
    private void showFatalErrorDialogAndShutdown(String title, Throwable e) {
        showAlertDialogAndWait(Alert.AlertType.ERROR, title, e.getMessage(), e.toString());
        Platform.exit();
        System.exit(1);
    }
}
