package chronologer.ui;

import chronologer.ChronologerMain;
import chronologer.command.Command;
import chronologer.parser.Parser;
import chronologer.storage.Storage;
import chronologer.task.TaskList;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;

/**
 * Main UI window of the application. Acts as a container for child UI elements.
 */
class MainWindow extends UiComponent<Stage> {
    private static final String FXML = "MainWindow.fxml";

    @FXML
    private AnchorPane timelineWindowHolder;
    @FXML
    private AnchorPane chatbotWindowHolder;

    private Stage baseStage;
    private Parser parser;
    private TaskList tasks;
    private Command command;
    private Storage storage;
    private String filePath = System.getProperty("user.dir") + "/src/ChronologerDatabase/ArrayList";
    private File file = new File(filePath);

    /**
     * Constructs the main UI window to house all the different child UI elements.
     *
     * @param baseStage Base stage of the application.
     * @param main      Main of Chronologer.
     */
    MainWindow(Stage baseStage, ChronologerMain main) {
        super(FXML, baseStage);

        this.baseStage = baseStage;
        this.tasks = main.tasks;
        this.storage = main.storage;
        placeUiComponents();
    }

    /**
     * Places UI components in the main GUI window.
     */
    private void placeUiComponents() {
        TimelineWindow timelineWindow = new TimelineWindow(command, parser, tasks);
        timelineWindowHolder.getChildren().add(timelineWindow.getRoot());

        ChatbotWindow chatbotWindow = new ChatbotWindow(command, parser, tasks, storage);
        chatbotWindowHolder.getChildren().add(chatbotWindow.getRoot());
    }

    Stage getBaseStage() {
        return baseStage;
    }

    /**
     * Shows the main GUI window.
     */
    void show() {
        baseStage.show();
    }

    void print(String message) {
    }
}
