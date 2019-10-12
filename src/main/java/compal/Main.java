package compal;

import compal.commons.Compal;
import compal.ui.Ui;
import compal.ui.UiManager;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Initializes GUI.
 */
public class Main extends Application {
    //Class Properties/Variables

    private Compal compal = new Compal();

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Initializes and sets up the GUI.
     * Pulls layout from file MainWindow.fxml.
     *
     * @param primaryStage The stage for GUI.
     * @throws Exception If there is GUI problems.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Ui ui = new UiManager(compal);
        ui.start(primaryStage);
    }
}
