package chronologer;

import chronologer.exception.ChronologerException;
import chronologer.storage.ChronologerStateList;
import chronologer.ui.UiManager;
import javafx.application.Application;
import javafx.stage.Stage;
import chronologer.ui.Ui;
import chronologer.storage.Storage;
import chronologer.task.TaskList;
import org.apache.commons.lang3.SerializationUtils;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Main class of the application which holds the UI as well as instantiates
 * storage components.
 */
public class ChronologerMain extends Application implements Serializable {

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
            this.tasks = storage.loadFile(file);
            ChronologerStateList.addState(SerializationUtils.clone(tasks.getTasks()));
        } catch (ChronologerException e) {
            this.tasks = new TaskList(new ArrayList<>());
            ChronologerStateList.addState(SerializationUtils.clone(tasks.getTasks()));
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