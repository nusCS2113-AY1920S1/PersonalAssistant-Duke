package duke.ui;

import duke.Duke;
import duke.Main;
import duke.commands.Command;
import duke.commands.ExitCommand;
import duke.commons.exceptions.DukeException;
import duke.commons.Messages;
import duke.storage.Storage;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

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

    private Duke duke;
    private static final String FXML = "MainWindow.fxml";
    private Stage primaryStage;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/user.png"));
    private static final int COMMAND_TIMEOUT_PERIOD = 1000;
    private Storage storage;
    private static final String FILE_PATH = "data/tasks.txt";
    private Ui ui = new Ui(dialogContainer);

    /**
     * Initialises the MainWindow.
     */
    public MainWindow(Stage primaryStage) {
        super(FXML, primaryStage);
        this.primaryStage = primaryStage;
        primaryStage.getScene().getStylesheets().addAll(
                this.getClass().getResource("/css/mainStyle.css").toExternalForm());
        storage = new Storage(FILE_PATH, ui);
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
        Ui ui = new Ui(dialogContainer);
        duke = new Duke(main, ui);
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        userInput.clear();
        //Echo user input
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage)
        );

        Future<Command> future = duke.getResponse(input);
        if (future != null) {
            try {
                System.out.println("going to get command");
                future.get(COMMAND_TIMEOUT_PERIOD, TimeUnit.MILLISECONDS);
                if (future.get() == null) {
                    ui.show(duke.getReply());
                } else if (!(future.get() instanceof ExitCommand)) {
                    future.get().execute(ui, storage);
                } else if (future.get() instanceof ExitCommand) {
                    duke.tryExitApp();
                }

            } catch (DukeException | InterruptedException | ExecutionException e) {
                if (e.getMessage().contains(Messages.UNKNOWN_COMMAND)) {
                    ui.showError(Messages.UNKNOWN_COMMAND);
                } else {
                    ui.showError(e.getMessage());
                }
            } catch (TimeoutException e) {
                ui.showError(Messages.REQUEST_TIMEOUT);
            }
        }


    }
}
