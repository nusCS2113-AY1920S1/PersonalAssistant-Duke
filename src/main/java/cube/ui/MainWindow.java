package cube.ui;

import javafx.stage.Stage;
import javafx.scene.layout.StackPane;
import javafx.fxml.FXML;

public class MainWindow extends UiManager<Stage> {
    public static final String FXML = "MainWindow.fxml";

    private Stage primaryStage;

    @FXML
    private StackPane commandBoxPlaceholder;

    public MainWindow (Stage primaryStage) {
        super(FXML, primaryStage);

        this.primaryStage = primaryStage;
    }

    public void show() {
        primaryStage.show();
    }

    public void initComponents() {
        CommandBox commandBox = new CommandBox();
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        primaryStage.hide();
    }

    /**
     * Shows the help window.
     */
    @FXML
    public void handleHelp() {
        //add a pop-up box here
    }

}
