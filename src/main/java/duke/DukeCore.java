package duke;

import duke.data.DukeObject;
import duke.data.GsonStorage;
import duke.data.PatientMap;
import duke.data.SearchResult;
import duke.exception.DukeException;
import duke.exception.DukeFatalException;
import duke.exception.DukeUtilException;
import duke.ui.Ui;
import duke.ui.UiManager;
import duke.ui.context.Context;
import duke.ui.context.UiContext;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

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
     * Constructs a DukeCore object with the specified stdtestout.
     */
    public DukeCore() {
        ui = new UiManager(this);
        uiContext = new UiContext();

        try {
            storage = new GsonStorage(storagePath);
            patientMap = new PatientMap(storage);
        } catch (DukeException e) {
            ui.showErrorDialogAndShutdown("Error encountered!", e);
        }
    }

    // TODO finish up
    /**
     * Displays results of a search.
     * @param searchTerm The term that was used to perform the search.
     * @param resultList The search results obtained.
     * @param parent The parent object that the search was performed in.
     * @throws DukeUtilException If the search result list is null.
     */
    public void showSearchResults(String searchTerm, List<? extends DukeObject> resultList,
                                  DukeObject parent) throws DukeUtilException {
        if (resultList == null) {
            throw new DukeUtilException("Search result list is null!");
        }
        SearchResult search = new SearchResult(searchTerm, resultList, parent);
        uiContext.setContext(Context.SEARCH, search);
        ui.print("Opening search results for " + searchTerm);
    }

    /**
     * Writes JSON file using patientMap HashMap.
     *
     * @throws DukeFatalException If the file writer cannot be setup.
     */
    public void writeJsonFile() throws DukeFatalException {
        storage.writeJsonFile(patientMap.getPatientList());
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
