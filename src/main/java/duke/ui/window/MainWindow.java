package duke.ui.window;

import duke.DukeCore;
import duke.command.Executor;
import duke.command.Parser;
import duke.data.DukeObject;
import duke.data.GsonStorage;
import duke.data.Impression;
import duke.data.Patient;
import duke.data.SearchResult;
import duke.exception.DukeException;
import duke.ui.UiElement;
import duke.ui.context.Context;
import duke.ui.context.UiContext;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * Main UI window of the application.
 * Acts as a container for child UI elements.
 */
public class MainWindow extends UiElement<Stage> {
    private static final String FXML = "MainWindow.fxml";

    @FXML
    private AnchorPane commandWindowHolder;
    @FXML
    private TabPane contextWindowHolder;
    @FXML
    private AnchorPane homeWindowHolder;
    @FXML
    private VBox helpWindowHolder;

    private Stage primaryStage;
    private UiContext uiContext;
    private ArrayList<Patient> patientList;
    private Executor executor;
    private Parser parser;
    private GsonStorage storage;

    private ContextWindow currentContextWindow;
    private Tab currentTab;
    private CommandWindow commandWindow;

    /**
     * Constructs the main UI window to house child UI elements.
     *
     * @param primaryStage Main stage of the application.
     * @param core         Core of Dr. Duke.
     */
    public MainWindow(Stage primaryStage, DukeCore core) {
        super(FXML, primaryStage);

        this.primaryStage = primaryStage;
        this.uiContext = core.uiContext;
        this.patientList = core.patientList.getPatientList();
        this.executor = new Executor(core);
        this.parser = new Parser(core.uiContext);
        this.storage = core.storage;

        placeChildUiElements();
    }

    /**
     * Places child UI elements in the main UI window.
     */
    private void placeChildUiElements() {
        currentContextWindow = new HomeContextWindow(patientList);
        currentTab = new Tab("Home", currentContextWindow.getRoot());
        contextWindowHolder.getTabs().add(currentTab);

        commandWindow = new CommandWindow(parser, executor);
        commandWindowHolder.getChildren().add(commandWindow.getRoot());

        try {
            HelpWindow helpWindow = new HelpWindow(storage, commandWindow.getInputTextField(), uiContext);
            helpWindowHolder.getChildren().add(helpWindow.getRoot());
        } catch (DukeException e) {
            print(e.getMessage());
        }

        // TODO: Add contexts here.
        uiContext.addListener(event -> {
            contextWindowHolder.getTabs().remove(currentTab);

            switch ((Context) event.getNewValue()) {
            case HOME:
                currentContextWindow = new HomeContextWindow(patientList);
                currentTab = new Tab("Home", currentContextWindow.getRoot());
                break;
            case PATIENT:
                currentContextWindow = new PatientContextWindow((Patient) uiContext.getObject());
                currentTab = new Tab("Patient", currentContextWindow.getRoot());
                break;
            case IMPRESSION:
                Impression impression = (Impression) uiContext.getObject();
                currentContextWindow = new ImpressionContextWindow(impression, (Patient) impression.getParent());
                currentTab = new Tab("Impression", currentContextWindow.getRoot());
                break;
            case SEARCH:
                SearchResult searchResult = (SearchResult) uiContext.getObject();
                currentContextWindow = new SearchContextWindow(searchResult);
                currentTab = new Tab("Search", currentContextWindow.getRoot());
                break;
            default:
                return;
            }

            contextWindowHolder.getTabs().add(currentTab);
            contextWindowHolder.getSelectionModel().select(currentTab);
        });
    }

    /**
     * Shows the main UI window.
     */
    public void show() {
        primaryStage.show();
    }

    /**
     * Prints message on the {@code CommandWindow}.
     *
     * @param message Output message.
     */
    public void print(String message) {
        commandWindow.print(message);
    }

    /**
     * Update UI window.
     */
    public void updateUi(String message) {
        print(message);
        currentContextWindow.updateUi();
    }

    /**
     * Retrieves indexed list of DukeObjects.
     * List is dependent on the current {@code UiContext}.
     *
     * @param type DukeObject type.
     * @return Indexed list of DukeObjects.
     */
    public List<DukeObject> getIndexedList(String type) {
        return currentContextWindow.getIndexedList(type);
    }
}
