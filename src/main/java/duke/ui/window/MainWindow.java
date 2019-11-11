package duke.ui.window;

import duke.DukeCore;
import duke.command.Executor;
import duke.command.Parser;
import duke.data.Impression;
import duke.data.Investigation;
import duke.data.Medicine;
import duke.data.Observation;
import duke.data.Patient;
import duke.data.Plan;
import duke.data.Result;
import duke.data.SearchResults;
import duke.data.storage.GsonStorage;
import duke.exception.DukeException;
import duke.exception.DukeFatalException;
import duke.ui.commons.UiElement;
import duke.ui.commons.UiStrings;
import duke.ui.context.Context;
import duke.ui.context.UiContext;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

import static duke.DukeCore.logger;

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
     * @throws DukeFatalException If {@code currentContextWindow} fails to initialise / load.
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
     * @throws DukeFatalException If the {@code currentContextWindow} to be updated cannot be initialised / loaded.
     */
    public void updateUi(String message) throws DukeFatalException {
        if (currentContextWindow != null) {
            currentContextWindow.updateUi();
        }

        showMessage(message);
    }

    /**
     * Initialises and places child UI elements in the main UI window.
     *
     * @throws DukeFatalException If the {@code currentContextWindow} to be updated cannot be initialised / loaded.
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
        logger.info(UiStrings.LOG_INFO_LAUNCH_HELP);

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
     * @throws DukeFatalException If the {@code currentContextWindow} to be updated cannot be initialised / loaded.
     * @see #attachListenerToUiContext()
     */
    private void initialiseContextWindow() throws DukeFatalException {
        logger.info(UiStrings.LOG_INFO_LAUNCH_HOME);

        currentContextWindow = new HomeContextWindow(patientList);
        currentTab = new Tab(Context.HOME.toString(), currentContextWindow.getRoot());
        contextWindowHolder.getTabs().add(currentTab);
    }

    /**
     * Initialises the command window, {@code commandWindow}.
     * The command window allows users to interact with the application through the sending of commands.
     */
    private void initialiseCommandWindow() {
        logger.info(UiStrings.LOG_INFO_LAUNCH_COMMAND);

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
            logger.info(UiStrings.LOG_INFO_SWITCH_CONTEXT);

            contextWindowHolder.getTabs().remove(currentTab);

            Context context = (Context) event.getNewValue();
            try {
                switch (context) {
                case HOME:
                    currentContextWindow = new HomeContextWindow(patientList);
                    break;
                case PATIENT:
                    Patient patient = (Patient) uiContext.getObject();
                    currentContextWindow = new PatientContextWindow(patient);
                    break;
                case IMPRESSION:
                    Impression impression = (Impression) uiContext.getObject();
                    currentContextWindow = new ImpressionContextWindow(impression, impression.getParent());
                    break;
                case PLAN:
                    Plan plan = (Plan) uiContext.getObject();
                    currentContextWindow = new PlanContextWindow(plan);
                    break;
                case INVESTIGATION:
                    Investigation investigation = (Investigation) uiContext.getObject();
                    currentContextWindow = new InvestigationContextWindow(investigation);
                    break;
                case OBSERVATION:
                    Observation observation = (Observation) uiContext.getObject();
                    currentContextWindow = new ObservationContextWindow(observation);
                    break;
                case RESULT:
                    Result result = (Result) uiContext.getObject();
                    currentContextWindow = new ResultContextWindow(result);
                    break;
                case MEDICINE:
                    Medicine medicine = (Medicine) uiContext.getObject();
                    currentContextWindow = new MedicineContextWindow(medicine);
                    break;
                case SEARCH:
                    SearchResults searchResults = (SearchResults) uiContext.getObject();
                    currentContextWindow = new SearchContextWindow(searchResults);
                    break;
                default:
                    return;
                }
            } catch (DukeFatalException e) {
                core.ui.showErrorDialogAndShutdown(UiStrings.MESSAGE_ERROR_OPEN_CONTEXT, e);
            }

            currentTab = new Tab(context.toString(), currentContextWindow.getRoot());
            contextWindowHolder.getTabs().add(currentTab);
            contextWindowHolder.getSelectionModel().select(currentTab);
        });
    }
}
