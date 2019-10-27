package duke.ui;

import duke.DukeCore;
import duke.command.Executor;
import duke.command.Parser;
import duke.data.Impression;
import duke.data.Patient;
import duke.data.PatientMap;
import duke.ui.window.HomeWindow;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Main UI window of the application.
 * Acts as a container for child UI elements.
 */
class MainWindow extends UiElement<Stage> {
    private static final String FXML = "MainWindow.fxml";

    @FXML
    private AnchorPane commandWindowHolder;
    @FXML
    private TabPane contextWindowHolder;
    @FXML
    private AnchorPane homeWindowHolder;

    private Stage primaryStage;
    private UiContext uiContext;
    private PatientMap patientMap;
    private Executor executor;
    private Parser parser;

    private CommandWindow commandWindow;
    private Tab homeTab;
    private Tab patientTab;
    private Tab impressionTab;

    /**
     * Constructs the main UI window to house child UI elements.
     *
     * @param primaryStage Main stage of the application.
     * @param core         Core of Dr. Duke.
     */
    MainWindow(Stage primaryStage, DukeCore core) {
        super(FXML, primaryStage);

        this.primaryStage = primaryStage;
        this.uiContext = core.uiContext;
        this.patientMap = core.patientMap;
        this.executor = new Executor(core);
        this.parser = new Parser(core.uiContext);

        placeChildUiElements();
    }

    /**
     * Places child UI elements in the main UI window.
     */
    private void placeChildUiElements() {
        commandWindow = new CommandWindow(executor, parser);
        commandWindowHolder.getChildren().add(commandWindow.getRoot());

        homeTab = new Tab("Home", new HomeWindow(patientMap).getRoot());
        contextWindowHolder.getTabs().add(homeTab);

        patientTab = new Tab("Patient", new PatientWindow(null).getRoot());
        contextWindowHolder.getTabs().add(patientTab);

        impressionTab = new Tab("Impression", new ImpressionWindow(null, null).getRoot());
        contextWindowHolder.getTabs().add(impressionTab);

        // TODO: Add contexts here.
        uiContext.addListener(evt -> {
            switch ((Context) evt.getNewValue()) {
            case HOME:
                contextWindowHolder.getTabs().remove(homeTab);
                homeTab = new Tab("Home", new HomeWindow(patientMap).getRoot());
                contextWindowHolder.getTabs().add(0, homeTab);
                contextWindowHolder.getSelectionModel().select(homeTab);
                break;
            case PATIENT:
                contextWindowHolder.getTabs().remove(patientTab);
                patientTab = new Tab("Patient", new PatientWindow((Patient) uiContext.getObject()).getRoot());
                contextWindowHolder.getTabs().add(1, patientTab);
                contextWindowHolder.getSelectionModel().select(patientTab);
                break;
            case IMPRESSION:
                contextWindowHolder.getTabs().remove(impressionTab);
                Impression impression = (Impression) uiContext.getObject();
                impressionTab = new Tab("Impression", new ImpressionWindow(impression,
                        (Patient)impression.getParent()).getRoot());
                contextWindowHolder.getTabs().add(2, impressionTab);
                contextWindowHolder.getSelectionModel().select(impressionTab);
                break;
            default:
                break;
            }
        });
    }

    /**
     * Shows the main UI window.
     */
    void show() {
        primaryStage.show();
    }

    /**
     * {@inheritDoc}
     */
    void print(String message) {
        commandWindow.print(message);
    }

    Stage getPrimaryStage() {
        return primaryStage;
    }
}
