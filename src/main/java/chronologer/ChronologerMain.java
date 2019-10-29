package chronologer;

import chronologer.exception.ChronologerException;
import chronologer.storage.ChronologerStateList;
import chronologer.ui.UiManager;
import javafx.application.Application;
import javafx.stage.Stage;
import chronologer.ui.Ui;
import chronologer.storage.Storage;
import chronologer.task.TaskList;

import java.io.File;
import java.util.ArrayList;

/**
 * Main class of the application which holds the UI as well as instantiates
 * storage components.
 */
public class ChronologerMain extends Application {

    public Ui ui;
    private static String filePath = System.getProperty("user.dir") + "/src/ChronologerDatabase/ArrayList";
    public Storage storage;
    public TaskList tasks;
    public File file = new File(filePath);

    /**
     * Constructs the ChronologerMain object.
     */
    public ChronologerMain() {
        ui = new UiManager(this);
        try {
            this.storage = new Storage(file);
            this.tasks = new TaskList(storage.loadFile(file));
            ChronologerStateList.initialState(tasks.getTasks());
        } catch (ChronologerException e) {
            this.tasks = new TaskList(new ArrayList<>());
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