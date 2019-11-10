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
    private static String version1Path = System.getProperty("user.dir") + "/src/ChronologerDatabase/Version1";
    private static String version2Path = System.getProperty("user.dir") + "/src/ChronologerDatabase/Version2";
    private static String version3Path = System.getProperty("user.dir") + "/src/ChronologerDatabase/Version3";
    public Storage storage;
    public TaskList tasks;
    public File file = new File(filePath);
    public File version1 = new File(version1Path);
    public File version2 = new File(version2Path);
    public File version3 = new File(version3Path);
    public ChronologerStateList history;

    /**
     * Constructs the ChronologerMain object.
     */
    public ChronologerMain() {
        ui = new UiManager(this);
        try {
            this.storage = new Storage(file);
            this.history = new ChronologerStateList(version1, version2, version3);
            this.tasks = storage.loadFile(file);
            history.addState(SerializationUtils.clone(tasks.getTasks()));
        } catch (ChronologerException e) {
            this.tasks = new TaskList(new ArrayList<>());
            history.addState(SerializationUtils.clone(tasks.getTasks()));
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