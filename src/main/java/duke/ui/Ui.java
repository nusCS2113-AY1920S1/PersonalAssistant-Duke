package duke.ui;

import duke.data.DukeObject;
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
     * Retrieves indexed list of DukeObjects.
     * List is dependent on the current {@code UiContext}.
     *
     * @param type DukeObject type.
     * @return Indexed list of DukeObjects.
     */
    List<DukeObject> getIndexedList(String type);
}
