package chronologer.ui;

import javafx.stage.Stage;

/**
 * Application Programming Interface (API) of the UI component of the application.
 */
public interface Ui {
    /**
     * Starts the UI and JavaFX application.
     *
     * @param primaryStage Stage created by the JavaFX system when the application first starts up.
     */
    void start(Stage primaryStage);

    /**
     * Prints message on the chatbox window.
     *
     * @param message Holds the message to be printed on the chatbot.
     */
    void print(String message);
}
