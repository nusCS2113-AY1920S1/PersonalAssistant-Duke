package duke.ui;

import duke.DukeCore;
import duke.exception.DukeFatalException;
import duke.ui.window.CommandWindow;
import duke.ui.window.ContextWindow;
import javafx.stage.Stage;

//@@author gowgos5
/**
 * API of the UI module of Dr. Duke.
 * Abstracts and exposes the UI module to external modules of Dr. Duke.
 */
public interface Ui {
    /**
     * Starts the UI (and Dr. Duke).
     * This helper function should not be called anywhere else except by {@link DukeCore}.
     *
     * @param primaryStage Stage created by the JavaFX system when Dr. Duke starts up.
     */
    void start(Stage primaryStage);

    /**
     * Terminates the UI (and Dr. Duke).
     * This helper function should not be called anywhere else except by {@link DukeCore}.
     */
    void stop();

    /**
     * Shows message on the {@link CommandWindow}.
     *
     * @param message Output message.
     */
    void showMessage(String message);

    /**
     * Updates {@link ContextWindow} and shows message on the {@link CommandWindow}.
     *
     * @param message Output message.
     * @throws DukeFatalException If the {@link ContextWindow} to be updated cannot be initialised / loaded.
     */
    void updateUi(String message) throws DukeFatalException;

    /**
     * Shows an error dialog with {@code errorTitle} and {@code error} message.
     * Exits the application only after the user has acknowledged the alert dialog.
     *
     * @param errorTitle Title of error dialog.
     * @param error      Thrown error.
     */
    void showErrorDialogAndShutdown(String errorTitle, Throwable error);
}
