package duke.ui.window;

import duke.DukeCore;
import duke.command.Executor;
import duke.command.Parser;
import duke.data.DukeObject;
import duke.data.storage.GsonStorage;
import duke.data.Impression;
import duke.data.Patient;
import duke.data.SearchResults;
import duke.exception.DukeException;
import duke.exception.DukeFatalException;
import duke.ui.commons.UiElement;
import duke.ui.commons.UiStrings;
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

//@@author gowgos5
/**
 * Main UI window of the application.
 * This window is the main interface between the user and Dr. Duke.
 * It also acts as a container for other child UI elements.
 */
public class MainWindow extends UiElement<Stage> {
    private static final String FXML = "MainWindow.fxml";

    @FXML
    private TabPane contextWindowHolder;
    @FXML
    private AnchorPane commandWindowHolder;
    @FXML
    private VBox helpWindowHolder;

    private Tab currentTab;
    private ContextWindow currentContextWindow;
    private CommandWindow commandWindow;
    private HelpWindow helpWindow;

    private Stage primaryStage;
    private DukeCore core;
    private UiContext uiContext;
    private ArrayList<Patient> patientList;
    private Parser parser;
    private Executor executor;
    private GsonStorage storage;

    /**
     * Constructs the main UI window to house other child UI elements.
     *
     * @param primaryStage Main stage of the application.
     * @param core         Core of Dr. Duke.
     */
    public MainWindow(Stage primaryStage, DukeCore core) throws DukeFatalException {
        super(FXML, primaryStage);

        this.primaryStage = primaryStage;
        this.core = core;
        this.uiContext = core.uiContext;
        this.patientList = core.patientData.getPatientList();
        this.parser = new Parser(core.uiContext);
        this.executor = new Executor(core);
        this.storage = core.storage;

        fillWithChildUiElements();
        attachListenerToUiContext();
    }

    /**
     * Shows the main UI window.
     */
    public void show() {
        primaryStage.show();
    }

    /**
     * Shows message on the {@code commandWindow}.
     *
     * @param message Output message.
     */
    public void showMessage(String message) {
        commandWindow.print(message);
    }

    /**
     * Updates {@code currentContextWindow} and shows message on the {@code commandWindow}.
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
        initialiseHelpWindow();
        initialiseContextWindow();
        initialiseCommandWindow();
    }

    /**
     * Initialises the help window, {@code helpWindow}.
     * The help window provides users with real-time visual feedback on the available commands in each context.
     */
    private void initialiseHelpWindow() {
        try {
            helpWindow = new HelpWindow(storage, uiContext);
            helpWindowHolder.getChildren().add(helpWindow.getRoot());
        } catch (DukeException e) {
            showMessage(e.getMessage());
        }
    }

    /**
     * Initialises the context window, {@code currentContextWindow}.
     * The application starts out at the HOME context.
     * The context window changes according to the current {@code uiContext}.
     *
     * @see #attachListenerToUiContext()
     */
    private void initialiseContextWindow() throws DukeFatalException {
        currentContextWindow = new HomeContextWindow(patientList);
        currentTab = new Tab(Context.HOME.toString(), currentContextWindow.getRoot());
        contextWindowHolder.getTabs().add(currentTab);
    }

    /**
     * Initialises the command window, {@code commandWindow}.
     * The command window allows users to interact with the application through the sending of commands.
     */
    private void initialiseCommandWindow() {
        commandWindow = new CommandWindow(parser, executor);
        commandWindow.attachTextListenerToTextField(helpWindow);
        commandWindowHolder.getChildren().add(commandWindow.getRoot());
    }

    /**
     * Attaches a listener to the {@code uiContext}.
     * This listener listens to changes in the current context and displays the corresponding {@link ContextWindow}
     * in the {@code contextWindowHolder}.
     */
    private void attachListenerToUiContext() {
        uiContext.addListener(event -> {
            contextWindowHolder.getTabs().remove(currentTab);

            try {
                switch ((Context) event.getNewValue()) {
                case HOME:
                    currentContextWindow = new HomeContextWindow(patientList);
                    currentTab = new Tab(Context.HOME.toString(), currentContextWindow.getRoot());
                    break;
                case PATIENT:
                    Patient patient = (Patient) uiContext.getObject();
                    currentContextWindow = new PatientContextWindow(patient);
                    currentTab = new Tab(Context.PATIENT.toString(), currentContextWindow.getRoot());
                    break;
                case IMPRESSION:
                    Impression impression = (Impression) uiContext.getObject();
                    currentContextWindow = new ImpressionContextWindow(impression, impression.getParent());
                    currentTab = new Tab(Context.IMPRESSION.toString(), currentContextWindow.getRoot());
                    break;
                case SEARCH:
                    SearchResults searchResults = (SearchResults) uiContext.getObject();
                    currentContextWindow = new SearchContextWindow(searchResults);
                    currentTab = new Tab(Context.SEARCH.toString(), currentContextWindow.getRoot());
                    break;
                default:
                    return;
                }
            } catch (DukeFatalException e) {
                core.ui.showErrorDialogAndShutdown(UiStrings.MESSAGE_ERROR_OPEN_CONTEXT, e);
            }

            contextWindowHolder.getTabs().add(currentTab);
            contextWindowHolder.getSelectionModel().select(currentTab);
        });
    }

    /**
     * Retrieves indexed list of DukeObjects.
     * The returned list is dependent on the current {@code UiContext}.
     *
     * @param type DukeObject type.
     * @return Indexed list of DukeObjects.
     */
    public List<DukeObject> getIndexedList(String type) {
        return currentContextWindow.getIndexedList(type);
    }
}
