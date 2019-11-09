package duke;

import duke.command.ObjCommand;
import duke.command.Parser;
import duke.data.DukeObject;
import duke.data.storage.GsonStorage;
import duke.data.PatientData;
import duke.data.SearchResults;
import duke.exception.DukeException;
import duke.exception.DukeFatalException;
import duke.ui.Ui;
import duke.ui.UiManager;
import duke.ui.context.UiContext;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main class of the application.
 * The core of Dr. Duke, which holds the UI and storage components.
 */
public class DukeCore extends Application {
    public static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
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
            setupLoggers();
        } catch (DukeFatalException e) {
            ui.showErrorDialogAndShutdown("Error encountered!", e);
        }
    }

    /**
     * Displays a set of search results, while storing a {@link ObjCommand} object (the one that calls the search),
     * so that it can resume execution after receiving the search results.
     *
     * @throws DukeFatalException If the file writer cannot be setup.
     */
    public void search(SearchResults results, ObjCommand objCmd) throws DukeException {
        queuedCmd = objCmd;
        ui.showMessage("Displaying objects matching " + results.getName() + ".");
        uiContext.open(results);
    }

    /**
     * Executes the queued ObjCommand with the object found from search.
     *
     * @throws DukeFatalException If the file writer cannot be setup.
     */
    public void executeQueuedCmd(DukeObject obj) throws DukeException {
        uiContext.moveBackOneContext();
        try {
            queuedCmd.execute(this, obj);
        } catch (ClassCastException excp) {
            logger.log(Level.SEVERE, "Wrong type of object returned from search!"
                    + System.lineSeparator() + excp.getMessage());
            ui.showMessage(queuedCmd.getClass().getSimpleName() + " failed! See log for details.");
        }
        queuedCmd = null;
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
    public void updateUi(String message) throws DukeFatalException {
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
        ui.stop();
    }


    private void setupLoggers() throws DukeFatalException {
        File logDir = new File("data/logs");
        if (!logDir.exists() && !logDir.mkdir()) {
            throw new DukeFatalException("Unable to create log folder, try checking your permissions?");
        }
        try {
            logger.addHandler(new FileHandler("data/logs/duke%u.log"));
            Parser.parserLogger.addHandler(new FileHandler("data/logs/parser%u.log"));
        } catch (IOException | SecurityException excp) {
            throw new DukeFatalException("Unable to create log files, try checking your permissions?");
        }
    }
}
