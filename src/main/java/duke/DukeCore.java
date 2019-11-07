package duke;

import duke.command.ObjCommand;
import duke.data.DukeObject;
import duke.data.GsonStorage;
import duke.data.PatientData;
import duke.data.SearchResults;
import duke.exception.DukeException;
import duke.exception.DukeFatalException;
import duke.ui.Ui;
import duke.ui.UiManager;
import duke.ui.context.Context;
import duke.ui.context.UiContext;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.io.File;

/**
 * Main class of the application.
 * The core of Dr. Duke, which holds the UI and storage components.
 */
public class DukeCore extends Application {
    private static final String storagePath = "data" + File.separator + "patients.json";

    public Ui ui;
    public UiContext uiContext;
    public GsonStorage storage;
    public PatientData patientData;
    public ObjCommand queuedCmd;

    /**
     * Constructs a DukeCore object with the specified stdtestout.
     */
    public DukeCore() {
        ui = new UiManager(this);
        uiContext = new UiContext();

        try {
            storage = new GsonStorage(storagePath);
            patientData = new PatientData(storage);
        } catch (DukeException e) {
            ui.showErrorDialogAndShutdown("Error encountered!", e);
        }
    }

    /**
     * Displays a set of search results, while storing a ObjCommand object (the one that calls the search), so that
     * it can resume execution after receiving the search results.
     *
     * @throws DukeFatalException If the file writer cannot be setup.
     */
    public void search(SearchResults results, ObjCommand objCmd) throws DukeException {
        queuedCmd = objCmd;
        uiContext.setContext(Context.SEARCH, results);
    }

    /**
     * Executes the queued ObjCommand with the object found from search.
     *
     * @throws DukeFatalException If the file writer cannot be setup.
     */
    public void executeQueuedCmd(DukeObject obj) throws DukeException {
        uiContext.moveBackOneContext();
        queuedCmd.execute(this, obj);
    }

    /**
     * Writes JSON file using patientData HashMap.
     *
     * @throws DukeFatalException If the file writer cannot be setup.
     */
    public void writeJsonFile() throws DukeFatalException {
        storage.writeJsonFile(patientData.getPatientList());
    }

    /**
     * Update UI.
     */
    public void updateUi(String message) {
        ui.updateUi(message);
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
        Platform.exit();
        System.exit(0);
    }
}
