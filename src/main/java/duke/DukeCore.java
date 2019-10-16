package duke;

import duke.exception.DukeFatalException;
import duke.exception.DukeResetException;
import duke.gui.Ui;
import duke.gui.UiManager;
import duke.task.Storage;
import duke.task.TaskList;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.File;

/**
 * Launching point for Dr. Duke. Contains the core which holds the UI manager, storage, and task list.
 */
public class DukeCore extends Application {
    private static final String FILE_PATH = "data" + File.separator + "tasks.tsv";

    public Storage storage;
    public TaskList taskList;
    public Ui ui;

    /**
     * Construct a DukeCore object.
     */
    public DukeCore() {
        try {
            storage = new Storage(FILE_PATH);
            taskList = new TaskList(storage);
        } catch (DukeResetException excp) {
            // Reset data file
            try {
                storage.writeTaskFile(""); //write empty string to data file
            } catch (DukeFatalException e) {
                stop();
            }

            taskList = new TaskList();
        } catch (DukeFatalException e) {
            stop();
        }

        // TODO: relocate mkdir to Storage class instead
        File dataDir = new File("data");
        if (!dataDir.exists()) {
            dataDir.mkdir();
        }

        ui = new UiManager(this);
    }

    /**
     * Main function.
     *
     * @param args Arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
    }
}
