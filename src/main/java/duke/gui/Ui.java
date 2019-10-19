package duke.gui;

import javafx.stage.Stage;

/**
 * Application Programming Interface (API) of the UI component of the application.
 */
public interface Ui {
    /**
     * Starts the UI (and the JavaFX application).
     *
     * @param primaryStage Stage created by the JavaFX system when the application first starts up.
     */
    void start(Stage primaryStage);

    /**
     * Print message on the command window.
     *
     * @param message Output message.
     */
    void print(String message);
}
