package duke;

import duke.data.GsonStorage;
import duke.data.PatientMap;
import duke.exception.DukeFatalException;
import duke.exception.DukeResetException;
import duke.ui.Ui;
import duke.ui.UiManager;
import duke.ui.context.UiContext;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

/**
 * Main class of the application.
 * The core of Dr. Duke, which holds the UI and storage components.
 */
public class DukeCore extends Application {
    private static final String storagePath = "data" + File.separator + "patients.json";

    public Ui ui;
    public UiContext uiContext;
    public GsonStorage storage;
    public PatientMap patientMap;

    /**
     * Constructs a DukeCore object.
     */
    public DukeCore() {
        ui = new UiManager(this);
        uiContext = new UiContext();

        try {
            try {
                storage = new GsonStorage(storagePath);
                patientMap = new PatientMap(storage);
            } catch (DukeResetException e) {
                // Reset data file
                patientMap = storage.resetAllData();
            }
        } catch (DukeFatalException | IOException e) {
            ui.showErrorDialogAndShutdown("Error encountered!", e);
        }
    }

    /**
     * Writes JSON file using patientMap HashMap.
     *
     * @throws DukeFatalException If the file writer cannot be setup.
     */
    public void writeJsonFile() throws DukeFatalException {
        storage.writeJsonFile(patientMap.getPatientHashMap());
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
