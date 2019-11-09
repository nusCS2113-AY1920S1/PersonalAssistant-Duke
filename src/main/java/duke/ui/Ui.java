package duke.ui;

import duke.DukeCore;
import duke.data.DukeObject;
import duke.exception.DukeFatalException;
import duke.ui.context.UiContext;
import duke.ui.window.CommandWindow;
import duke.ui.window.ContextWindow;
import javafx.stage.Stage;

import java.util.List;

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
     * Prints message on the {@link CommandWindow}.
     *
     * @param message Output message.
     */
    void showMessage(String message);

    /**
     * Updates {@link ContextWindow} and prints message on the {@link CommandWindow}.
     *
     * @param message Output message.
     */
    void updateUi(String message) throws DukeFatalException;

    /**
     * Shows an error dialog with {@code errorTitle} and {@code error}.
     * Exits the application after the user has acknowledged the alert dialog.
     *
     * @param errorTitle Title of error dialog.
     * @param error      Thrown error.
     */
    void showErrorDialogAndShutdown(String errorTitle, Throwable error);

    /* TODO: TEMPORARY */
    /**
     * Retrieves indexed list of DukeObjects.
     * List is dependent on the current {@link UiContext}.
     *
     * @param type DukeObject type.
     * @return UI indexed list of DukeObjects.
     */
    List<DukeObject> getIndexedList(String type);
}
