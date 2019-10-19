package duke;

import duke.data.GsonStorage;
import duke.data.PatientMap;
import duke.data.TaskList;
import duke.exception.DukeFatalException;
import duke.exception.DukeResetException;
import duke.gui.Ui;
import duke.gui.UiManager;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.File;

/**
 * Main class of the application.
 * The core of Dr. Duke, which holds the UI and storage.
 */
public class DukeCore extends Application {
    private static final String FILE_PATH = "data" + File.separator + "patients.json";

    public Ui ui;
    public GsonStorage storage;
    public PatientMap patientMap;
    public TaskList taskList = null; //deprecated
    
    /**
     * Entry point into the application.
     *
     * @param args Supplied command-line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Constructs a DukeCore object.
     */
    public DukeCore() {
        ui = new UiManager(this);

        try {
            try {
                storage = new GsonStorage(FILE_PATH);
                patientMap = new PatientMap(storage);
            } catch (DukeResetException e) {
                // Reset data file
                patientMap = new PatientMap();
                storage.writeJsonFile(); //write empty data structure to data file
            }
        } catch (DukeFatalException e) {
            stop();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start(Stage primaryStage) {
        ui.start(primaryStage);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stop() {
        // TODO: Terminate application gracefully
    }
}
