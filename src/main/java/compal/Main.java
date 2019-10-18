package compal;

import compal.ui.Ui;
import compal.ui.UiManager;
import compal.ui.UiUtil;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Initializes GUI.
 */
public class Main extends Application {

    private Ui ui;


    /**
     * Constructs a new Main object.
     */
    public Main() {
        UiUtil uiUtil = new UiUtil();
        this.ui = new UiManager(uiUtil);
    }

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Initializes and sets up the GUI.
     *
     * @param primaryStage The stage for GUI.
     */
    @Override
    public void start(Stage primaryStage) {
        ui.start(primaryStage);
    }
}
