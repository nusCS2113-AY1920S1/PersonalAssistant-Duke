package duke.ui;

import duke.Main;
import duke.commands.results.CommandResult;
import duke.commands.results.CommandResultCalender;
import duke.commands.results.CommandResultExit;
import duke.commands.results.CommandResultImage;
import duke.commands.results.CommandResultMap;
import duke.commons.exceptions.DukeException;
import duke.logic.LogicManager;

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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Controller for MainWindow. Provides the layout for the other controls.
 */
public class MainWindow extends UiPart<Stage> {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

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
        dukeShow("Hi, welcome to SGTravel.");
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
        dukeResponse(input);
    }

    private void dukeResponse(String input) {
        Platform.runLater(() -> {
            try {
                CommandResult result = logic.execute(input);
                dukeShow(result);

                if (result instanceof CommandResultExit) {
                    tryExitApp();
                } else if (result instanceof CommandResultCalender) {
                    new CalendarWindow((CommandResultCalender) result).show();
                } else if (result instanceof CommandResultMap) {
                    new MapWindow((CommandResultMap) result).show();
                }

            } catch (DukeException e) {
                dukeShow(e.getMessage());
            }
        });
    }

    private void echoUserInput(String input) {
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage)
        );
    }

    private boolean isEmpty(String input) {
        return "".equals(input);
    }

    private String getUserInput() {
        String input = userInput.getText().strip();
        userInput.clear();
        return input;
    }

    /** Shows message(s) to the user.
     */
    private void dukeShow(String msg) {
        dialogContainer.getChildren().addAll(
                DialogBox.getDukeDialog(msg, dukeImage)
        );
    }

    private void dukeShow(CommandResult commandResult) {
        if (commandResult instanceof CommandResultImage) {
            dukeShowImage(commandResult.getMessage(), ((CommandResultImage) commandResult).getImage());
            return;
        }

        if (commandResult != null) {
            dukeShow(commandResult.getMessage());
        }
    }

    /**
     * Shows an image in dialogBoxImage to the user.
     * @param message The message to show.
     * @param image The image to show.
     */
    private void dukeShowImage(String message, Image image) {
        dialogContainer.getChildren().addAll(
                DialogBoxImage.getDukeDialog(message, dukeImage, image)
        );
    }



    private void tryExitApp() {
        try {
            main.stop();
        } catch (Exception e) {
            dukeShow("Exit app failed" + e.getMessage());
        }
    }
}
