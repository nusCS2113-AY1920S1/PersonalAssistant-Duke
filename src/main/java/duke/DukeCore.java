package duke;

import duke.exception.DukeFatalException;
import duke.exception.DukeResetException;
import duke.task.GsonStorage;
import duke.task.PatientMap;
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

    public GsonStorage storage;
    public TaskList taskList = null;
    public PatientMap patientMap;
    public Ui ui;

    /**
     * Construct a DukeCore object.
     */
    public DukeCore() {
        ui = new UiManager(this);
        try {
            try {
                storage = new GsonStorage(FILE_PATH);
                patientMap = new PatientMap(storage);
            } catch (DukeResetException excp) {
                // Reset data file
                patientMap = new PatientMap();
                storage.writeJsonFile(); //write empty data structure to data file
            }
        } catch (DukeFatalException e) {
            stop();
        }
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
