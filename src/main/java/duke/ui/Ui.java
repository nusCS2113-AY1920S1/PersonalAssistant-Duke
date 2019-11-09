package duke.ui;

import duke.data.DukeObject;
import duke.exception.DukeFatalException;
import javafx.stage.Stage;

import java.util.List;

/**
 * API of the UI component of the application.
 */
public interface Ui {
    /**
     * Starts the UI (and the JavaFX application).
     *
     * @param primaryStage Stage created by the JavaFX system when the application starts up.
     */
    void start(Stage primaryStage);

    /**
     * Prints message on the {@code CommandWindow}.
     *
     * @param message Output message.
     */
    void print(String message);

    /**
     * Update UI of current context.
     *
     * @param message Output message.
     */
    void updateUi(String message) throws DukeFatalException;

    /**
     * Displays an info pop-up dialog on screen.
     *
     * @param title   Title of dialog.
     * @param message Dialog message.
     */
    void showInfoDialog(String title, String message);

    /**
     * Shows an error alert dialog with {@code title} and error message, {@code e}.
     * Exits the application after the user has closed the alert dialog.
     *
     * @param title Title of error dialog.
     * @param e     Thrown Error.
     */
    void showErrorDialogAndShutdown(String title, Throwable e);

    /**
     * Retrieves indexed list of DukeObjects.
     * List is dependent on the current {@code UiContext}.
     *
     * @param type DukeObject type.
     * @return Indexed list of DukeObjects.
     */
    List<DukeObject> getIndexedList(String type);
}
