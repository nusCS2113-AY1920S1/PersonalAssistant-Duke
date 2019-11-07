package duke;

import duke.data.GsonStorage;
import duke.data.PatientList;
import duke.exception.DukeException;
import duke.exception.DukeFatalException;
import duke.ui.Ui;
import duke.ui.UiManager;
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
    public PatientList patientList;

    /**
     * Constructs a DukeCore object with the specified stdtestout.
     */
    public DukeCore() {
        ui = new UiManager(this);
        uiContext = new UiContext();

        try {
            storage = new GsonStorage(storagePath);
            patientList = new PatientList(storage);
        } catch (DukeException e) {
            ui.showErrorDialogAndShutdown("Error encountered!", e);
        }
    }

    /**
     * Writes JSON file using patientList HashMap.
     *
     * @throws DukeFatalException If the file writer cannot be setup.
     */
    public void writeJsonFile() throws DukeFatalException {
        storage.writeJsonFile(patientList.getPatientList());
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
