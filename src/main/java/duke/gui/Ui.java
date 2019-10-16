package duke.gui;

import javafx.stage.Stage;

/**
 * API of UI component.
 */
public interface Ui {
    /**
     * Starts the UI (and the application).
     */
    void start(Stage stage);

    /**
     * Print message: TODO.
     */
    void print(String output);
}
