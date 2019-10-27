package chronologer;

import chronologer.exception.ChronologerException;
import chronologer.ui.UiManager;
import javafx.application.Application;
import javafx.stage.Stage;
import chronologer.ui.Ui;
import chronologer.storage.Storage;
import chronologer.task.TaskList;
import chronologer.parser.Parser;

import java.io.File;
import java.util.ArrayList;

/**
 * Main class of the application.
 * The core of Dr. Duke, which holds the UI and storage components.
 */
public class ChronologerMain extends Application {

    public Ui ui;
    private static String filePath = System.getProperty("user.dir") + "/src/ChronologerDatabase/ArrayList";
    public Storage storage;
    public TaskList tasks;
    public Parser parser;
    public File file = new File(filePath);

    /**
     * Constructs the ChronologerMain object.
     */
    public ChronologerMain() {
        ui = new UiManager(this);
        try {
            storage = new Storage(file);
            tasks = new TaskList(storage.loadFile(file));
        } catch (ChronologerException e) {
            tasks = new TaskList(new ArrayList<>());
        }
    }

    @Override
    public void start(Stage primaryStage) {
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
    }
}