package duke.ui.window;

import duke.DukeCore;
import duke.command.Executor;
import duke.command.Parser;
import duke.data.DukeObject;
import duke.data.GsonStorage;
import duke.data.Impression;
import duke.data.Patient;
import duke.data.SearchResults;
import duke.exception.DukeException;
import duke.exception.DukeFatalException;
import duke.ui.commons.UiElement;
import duke.ui.context.Context;
import duke.ui.context.UiContext;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * Main UI window of the application.
 * This window is the main interface between the user and Dr. Duke.
 * It also acts as a container for other child UI elements.
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

    private DukeCore core;
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
     * Constructs the main UI window to house other child UI elements.
     *
     * @param primaryStage Main stage of the application.
     * @param core         Core of Dr. Duke.
     */
    public MainWindow(Stage primaryStage, DukeCore core) throws DukeFatalException {
        super(FXML, primaryStage);

        this.core = core;
        this.primaryStage = primaryStage;
        this.uiContext = core.uiContext;
        this.patientList = core.patientData.getPatientList();
        this.executor = new Executor(core);
        this.parser = new Parser(core.uiContext);
        this.storage = core.storage;

        fillWithChildUiElements();
        attachListenerToContext();
    }

    /**
     * Shows the main UI window.
     */
    public void show() {
        primaryStage.show();
    }

    /**
     * Prints message on the {@code commandWindow}.
     *
     * @param message Output message.
     */
    public void showMessage(String message) {
        commandWindow.print(message);
    }

    /**
     * Updates {@code currentContextWindow} and prints message on the {@code commandWindow}.
     *
     * @param message Output message.
     */
    public void updateUi(String message) throws DukeFatalException {
        currentContextWindow.updateUi();
        showMessage(message);
    }

    /**
     * Initialises and places child UI elements in the main UI window.
     */
    private void fillWithChildUiElements() throws DukeFatalException {
        initialiseContextWindow();
        initialiseCommandWindow();
        initialiseHelpWindow();
    }

    /**
     * Initialises the context window, {@code currentContextWindow}.
     * The context window changes according to the current {@code uiContext}.
     *
     * @see #attachListenerToContext()
     */
    private void initialiseContextWindow() throws DukeFatalException {
        currentContextWindow = new HomeContextWindow(patientList);
        currentTab = new Tab("Home", currentContextWindow.getRoot());
        contextWindowHolder.getTabs().add(currentTab);
    }

    /**
     * Initialises the help window, {@code helpWindow}.
     * The help window provides users with real-time visual feedback on the available commands of Dr. Duke.
     */
    private void initialiseHelpWindow() {
        try {
            TextArea inputTextField = commandWindow.getInputTextField();
            HelpWindow helpWindow = new HelpWindow(storage, inputTextField, uiContext);
            helpWindowHolder.getChildren().add(helpWindow.getRoot());
        } catch (DukeException e) {
            showMessage(e.getMessage());
        }
    }

    /**
     * Initialises the command window, {@code commandWindow}.
     * The command window allows users to interact with the application through the sending of commands.
     */
    private void initialiseCommandWindow() {
        commandWindow = new CommandWindow(parser, executor);
        commandWindowHolder.getChildren().add(commandWindow.getRoot());
    }

    /**
     * Attaches a listener to the {@code uiContext}.
     * This listener listens to changes in the current context and displays the corresponding {@link ContextWindow}
     * in the {@code contextWindowHolder}.
     */
    private void attachListenerToContext() throws DukeFatalException {
        // TODO: Tabs should show sequences of back operations.
        // TODO: Format code block
        uiContext.addListener(event -> {
            contextWindowHolder.getTabs().remove(currentTab);

            try {
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
                    currentContextWindow = new ImpressionContextWindow(impression, impression.getParent());
                    currentTab = new Tab("Impression", currentContextWindow.getRoot());
                    break;
                case SEARCH:
                    SearchResults searchResults = (SearchResults) uiContext.getObject();
                    currentContextWindow = new SearchContextWindow(searchResults);
                    currentTab = new Tab("Search", currentContextWindow.getRoot());
                    break;
                default:
                    return;
                }
            } catch (DukeFatalException e) {
                core.ui.showErrorDialogAndShutdown("Unable to open context window.", e);
            }

            contextWindowHolder.getTabs().add(currentTab);
            contextWindowHolder.getSelectionModel().select(currentTab);
        });
    }

    /* TODO: TEMPORARY */
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