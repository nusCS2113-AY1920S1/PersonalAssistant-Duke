package duke.gui;

import duke.DukeCore;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * The Main Window. Provides the most basic application layout to place other UI elements.
 */
public class MainWindow extends UiComponent<Stage> {
    private static final String FXML = "MainWindow.fxml";

    @FXML
    private AnchorPane commandWindowPlaceholder;
    @FXML
    private TabPane contextWindowPlaceholder;
    @FXML
    private AnchorPane homeWindowPlaceholder;

    private Stage primaryStage;
    private CommandWindow commandWindow;
    private HomeWindow homeWindow;

    private DukeCore core;

    /**
     * Create the main window to house other UI elements.
     *
     * @param primaryStage The main stage of the application.
     * @param core         DukeCore object.
     */
    public MainWindow(Stage primaryStage, DukeCore core) {
        super(FXML, primaryStage);
        this.primaryStage = primaryStage;
        this.core = core;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Place all child views in the main window.
     */
    public void placeChildViews() {
        commandWindow = new CommandWindow(core);
        commandWindowPlaceholder.getChildren().add(commandWindow.getRoot());

        homeWindow = new HomeWindow();
        Tab homeTab = new Tab("Home", homeWindow.getRoot());
        contextWindowPlaceholder.getTabs().add(homeTab);
    }

    void show() {
        primaryStage.show();
    }

    void print(String output) {
        commandWindow.print(output);
    }
}
