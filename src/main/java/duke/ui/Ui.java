package duke.ui;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.stage.Stage;

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
     * Retrieves list of UI cards in current {@code UiContext}.
     *
     * @return List of UI cards.
     */
    ObservableList<Node> getCardList();
}
