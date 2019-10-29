package duke.ui.window;

import duke.DukeCore;
import duke.command.Executor;
import duke.command.Parser;
import duke.data.Impression;
import duke.data.Patient;
import duke.data.SearchResult;
import duke.ui.UiElement;
import duke.ui.context.Context;
import duke.ui.context.UiContext;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

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

    private Stage primaryStage;
    private UiContext uiContext;
    private ObservableMap<String, Patient> patientObservableMap;
    private Executor executor;
    private Parser parser;

    private CommandWindow commandWindow;
    private HomeWindow homeWindow;
    private PatientWindow patientWindow;
    private Tab homeTab;
    private Tab patientTab;
    private Tab impressionTab;
    private Tab searchTab;

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
        this.patientObservableMap = core.patientMap.getPatientObservableMap();
        this.executor = new Executor(core);
        this.parser = new Parser(core.uiContext);

        placeChildUiElements();
    }

    /**
     * Places child UI elements in the main UI window.
     */
    private void placeChildUiElements() {
        commandWindow = new CommandWindow(parser, executor);
        commandWindowHolder.getChildren().add(commandWindow.getRoot());

        homeWindow = new HomeWindow(patientObservableMap);
        homeTab = new Tab("Home", homeWindow.getRoot());
        contextWindowHolder.getTabs().add(homeTab);

        // TODO: Add contexts here.
        uiContext.addListener(event -> {
            switch ((Context) event.getNewValue()) {
            case HOME:
                contextWindowHolder.getTabs().remove(homeTab);
                homeTab = new Tab("Home", new HomeWindow(patientObservableMap).getRoot());
                contextWindowHolder.getTabs().add(0, homeTab);
                contextWindowHolder.getSelectionModel().select(homeTab);
                break;
            case PATIENT:
                if (patientTab != null) {
                    contextWindowHolder.getTabs().remove(patientTab);
                }

                patientTab = new Tab("Patient", new PatientWindow((Patient) uiContext.getObject(),
                        commandWindow).getRoot());
                contextWindowHolder.getTabs().add(1, patientTab);
                contextWindowHolder.getSelectionModel().select(patientTab);
                break;
            case IMPRESSION:
                if (impressionTab != null) {
                    contextWindowHolder.getTabs().remove(impressionTab);
                }

                Impression impression = (Impression) uiContext.getObject();
                impressionTab = new Tab("Impression", new ImpressionWindow(impression,
                        (Patient) impression.getParent()).getRoot());
                contextWindowHolder.getTabs().add(2, impressionTab);
                contextWindowHolder.getSelectionModel().select(impressionTab);
                break;
            case SEARCH:
                contextWindowHolder.getTabs().remove(searchTab);
                SearchResult searchResult = (SearchResult) uiContext.getObject();
                searchTab = new Tab("Search", new SearchWindow(searchResult).getRoot());
                contextWindowHolder.getTabs().add(3, searchTab);
                contextWindowHolder.getSelectionModel().select(searchTab);
                break;
            default:
                break;
            }
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
     * Retrieves list of UI cards in current {@code UiContext}.
     *
     * @return List of UI cards.
     */
    public ObservableList<Node> getCardList() {
        switch (uiContext.getContext()) {
        case HOME:
            return homeWindow.getPatientCardList();
        case PATIENT:
            return patientWindow.getCardList();
        case EVIDENCE:
        case TREATMENT:
        case IMPRESSION:
        case INVESTIGATION:
        default:
            break;
        }

        return null;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }
}
