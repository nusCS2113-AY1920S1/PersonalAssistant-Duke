package duke.ui;

import duke.Main;
import duke.commons.Messages;
import duke.commons.exceptions.FileLoadFailException;
import duke.logic.commands.results.CommandResult;
import duke.logic.commands.results.CommandResultCalender;
import duke.logic.commands.results.CommandResultExit;
import duke.logic.commands.results.CommandResultImage;
import duke.logic.commands.results.CommandResultMap;
import duke.commons.exceptions.DukeException;
import duke.logic.LogicManager;
import duke.logic.commands.results.PanelResult;
import duke.ui.calendar.CalendarWindow;
import duke.ui.dialogbox.DialogBox;
import duke.ui.dialogbox.DialogBoxImage;
import duke.ui.map.MapWindow;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controller for MainWindow. Provides the layout for the other controls.
 */
public class MainWindow extends UiPart<Stage> {
    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;
    @FXML
    private AnchorPane sidePanel;

    private LogicManager logic;
    private static final String FXML = "MainWindow.fxml";
    private Stage primaryStage;
    private Main main;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/user.png"));
    private Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/duke.png"));

    /**
     * Initialises the MainWindow.
     */
    public MainWindow(Stage primaryStage) {
        super(FXML, primaryStage);
        this.primaryStage = primaryStage;
        primaryStage.getScene().getStylesheets().addAll(
                this.getClass().getResource("/css/mainStyle.css").toExternalForm());
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        logger.log(Level.INFO, "Starting user interface up...");
    }

    /**
     * Shows the application.
     */
    public void show() {
        primaryStage.show();
    }

    /**
     * Initialises the logic and Ui component of Duke.
     */
    public void initialise(Main main) {
        this.main = main;
        logic = new LogicManager();
        sgTravelShow(Messages.STARTUP_WELCOME_MESSAGE + logic.getName());
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply.
     */
    @FXML
    private void handleUserInput() {
        String input = getUserInput();
        if (isEmpty(input)) {
            return;
        }
        echoUserInput(input);
        sgTravelResponse(input);
    }

    @FXML
    private void handleKeyPress(KeyEvent keyEvent) {
        panelResponse(keyEvent);
    }

    private void panelResponse(KeyEvent keyEvent) {
        PanelResult result = logic.execute(keyEvent.getCode());
        panelShow(result);
    }

    private void sgTravelResponse(String input) {
        Platform.runLater(() -> {
            try {
                CommandResult result = logic.execute(input);
                sgTravelShow(result);

                if (result instanceof CommandResultExit) {
                    tryExitApp();
                }
                if (result instanceof CommandResultCalender) {
                    new CalendarWindow((CommandResultCalender) result).show();
                }
                if (result instanceof CommandResultMap) {
                    new MapWindow((CommandResultMap) result).show();
                }

            } catch (DukeException | FileNotFoundException e) {
                sgTravelShow(e.getMessage());
            }
        });
    }

    /**
     * Shows message(s) to the user.
     */
    private void sgTravelShow(CommandResult commandResult) {
        if (commandResult instanceof CommandResultImage) {
            sgTravelShow(commandResult.getMessage(), ((CommandResultImage) commandResult).getImage());
            return;
        }
        assert (commandResult != null);
        sgTravelShow(commandResult.getMessage());
    }

    private void sgTravelShow(String msg) {
        dialogContainer.getChildren().addAll(
                DialogBox.getDukeDialog(msg, dukeImage)
        );
    }

    /**
     * Shows an image in dialogBoxImage to the user.
     * @param message The message to show.
     * @param image The image to show.
     */
    private void sgTravelShow(String message, Image image) {
        dialogContainer.getChildren().addAll(
                DialogBoxImage.getDukeDialog(message, dukeImage, image)
        );
    }

    private void panelShow(PanelResult result) {
        sidePanel.getChildren().clear();
        sidePanel.getChildren().add(SidePanel.getPanel(result));
    }

    private void tryExitApp() {
        try {
            main.stop();
        } catch (Exception e) {
            sgTravelShow("Exit app failed" + e.getMessage());
        }
    }

    private void echoUserInput(String input) {
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage)
        );
    }

    private String getUserInput() {
        String input = userInput.getText().strip();
        userInput.clear();
        return input;
    }

    private boolean isEmpty(String input) {
        return "".equals(input);
    }
}
