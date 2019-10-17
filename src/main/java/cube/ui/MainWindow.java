package cube.ui;

import javafx.stage.Stage;
import javafx.fxml.FXML;

public class MainWindow extends UiManager<Stage> {
    public static final String FXML = "MainWindow.fxml";

    private Stage primaryStage;

    public MainWindow (Stage primaryStage) {
        super(FXML, primaryStage);

        this.primaryStage = primaryStage;
    }

    public void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        primaryStage.hide();
    }

    @FXML
    public void handleHelp() {

    }

}
