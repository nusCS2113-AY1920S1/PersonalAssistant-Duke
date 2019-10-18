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
    private UiUtil uiUtil;


    /**
     * Constructs a new Main object.
     */
    public Main() {
        this.uiUtil = new UiUtil();
        this.ui = new UiManager(uiUtil);
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
