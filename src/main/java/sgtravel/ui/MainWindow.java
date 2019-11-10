package sgtravel.ui;

import sgtravel.Main;
import sgtravel.commons.Messages;
import sgtravel.logic.commands.results.CommandResult;
import sgtravel.logic.commands.results.CommandResultCalender;
import sgtravel.logic.commands.results.CommandResultExit;
import sgtravel.logic.commands.results.CommandResultImage;
import sgtravel.logic.commands.results.CommandResultMap;
import sgtravel.commons.exceptions.DukeException;
import sgtravel.logic.LogicManager;
import sgtravel.logic.commands.results.PanelResult;
import sgtravel.model.Model;
import sgtravel.model.lists.RouteList;
import sgtravel.model.planning.Itinerary;
import sgtravel.ui.calendar.CalendarWindow;
import sgtravel.ui.dialogbox.DialogBox;
import sgtravel.ui.dialogbox.DialogBoxImage;
import sgtravel.ui.map.MapWindow;

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

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controller for MainWindow. Provides the layout for the other controls.
 */
public class MainWindow extends UiPart<Stage> {
    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private static final String FXML = "MainWindow.fxml";
    private LogicManager logic;
    private Stage primaryStage;
    private Main main;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/user.png"));
    private Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/duke.png"));

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

    /**
     * Initialises the MainWindow.
     *
     * @param primaryStage The stage for the MainWindow.
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
     *
     * @param main The main object.
     */
    public void initialise(Main main) {
        this.main = main;
        logic = new LogicManager();
        sgTravelShowWelcome();
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

    /**
     * Handles the event of a key press.
     *
     * @param keyEvent The event of a key press.
     */
    @FXML
    private void handleKeyPress(KeyEvent keyEvent) {
        panelResponse(keyEvent);
    }

    /**
     * Handles the event which triggers a PanelResult.
     *
     * @param keyEvent The event which triggers a PanelResult.
     */
    private void panelResponse(KeyEvent keyEvent) {
        PanelResult result = logic.execute(keyEvent.getCode());
        panelShow(result);
    }

    /**
     * Handles the response that SGTravel should do from a user input.
     *
     * @param input The user input.
     */
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

            } catch (DukeException e) {
                sgTravelShow(e.getMessage());
            }
        });
    }

    /**
     * Shows message(s) to the user.
     *
     * @param commandResult The CommandResult from the user input.
     */
    private void sgTravelShow(CommandResult commandResult) {
        if (commandResult instanceof CommandResultImage) {
            sgTravelShow(commandResult.getMessage(), ((CommandResultImage) commandResult).getImage());
            return;
        }
        assert (commandResult != null);
        sgTravelShow(commandResult.getMessage());
    }

    /**
     * Alternative method to show message(s) to the user.
     *
     * @param msg The String message to show.
     */
    private void sgTravelShow(String msg) {
        dialogContainer.getChildren().addAll(
                DialogBox.getDialog(msg, dukeImage)
        );
    }

    /**
     * Shows an image in dialogBoxImage to the user.
     *
     * @param message The message to show.
     * @param image The image to show.
     */
    private void sgTravelShow(String message, Image image) {
        dialogContainer.getChildren().addAll(
                DialogBoxImage.getDialog(message, dukeImage, image)
        );
    }

    /**
     * Shows the PanelResult for the SidePanel.
     *
     * @param result The PanelResult to display.
     */
    private void panelShow(PanelResult result) {
        sidePanel.getChildren().clear();
        sidePanel.getChildren().add(SidePanel.getPanel(result));
    }

    /**
     * Shows the welcome message.
     */
    private void sgTravelShowWelcome() {
        String message = logic.getWelcomeMessage();
        sgTravelShow(message);
    }

    /**
     * Tries to exit SGTravel.
     */
    private void tryExitApp() {
        try {
            main.stop();
        } catch (Exception e) {
            sgTravelShow("Exit app failed" + e.getMessage());
        }
    }

    /**
     * Echos user input and displays it.
     *
     * @param input The user input.
     */
    private void echoUserInput(String input) {
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage)
        );
    }

    /**
     * Gets the user input from the TextField.
     *
     * @return input The user input.
     */
    private String getUserInput() {
        String input = userInput.getText().strip();
        userInput.clear();
        return input;
    }

    /**
     * Checks whether a String is empty.
     *
     * @param input The String to check.
     * @return true If the String is empty.
     */
    private boolean isEmpty(String input) {
        return "".equals(input);
    }
}
