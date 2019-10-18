package compal;

import compal.logic.LogicManager;
import compal.storage.TaskStorageManager;
import compal.storage.UserStorageManager;
import compal.ui.*;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Initializes GUI.
 */
public class Main extends Application {

    private Ui ui;
    private UiParts uiParts;


    /**
     * Constructs a new Main object.
     */
    public Main() {
        this.uiParts = new UiParts();
        this.ui = new UiManager(uiParts);
    }

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Initializes and sets up the GUI.
     *
     * @param primaryStage The stage for GUI.
     * @throws Exception If there is GUI problems.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        //UI start
        ui.start(primaryStage);
    }
}
