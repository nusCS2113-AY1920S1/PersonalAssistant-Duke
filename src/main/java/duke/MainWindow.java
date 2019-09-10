package duke;

import duke.command.Command;
import duke.command.ExitCommand;
import duke.dukeexception.DukeException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import duke.ui.Ui;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Controller for MainWindow. Provides the layout for the other controls.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Duke duke;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/DaDuke.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Setting up Duke GUI.
     */
    public void setDuke(Duke d) {
        duke = d;
        dialogContainer.getChildren().add(
                DialogBox.getDukeDialog(Ui.showWelcomeGui(), dukeImage)
        );
    }

    Timer timer = new Timer();
    TimerTask exitDuke = new TimerTask() {
        public void run() {
            System.exit(0);
        }
    };

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response;
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage)
        );
        try {
            Command cmd = duke.getCommand(input);
            if (cmd instanceof ExitCommand) {
                duke.saveState(cmd);
                response = Ui.showLineGui() + Ui.showByeGui() + Ui.showLineGui();
                dialogContainer.getChildren().add(
                        DialogBox.getDukeDialog(response, dukeImage)
                );
                timer.schedule(exitDuke, new Date(System.currentTimeMillis() + 500));
            } else {
                response = Ui.showLineGui() + duke.executeCommand(cmd) + Ui.showLineGui();
                dialogContainer.getChildren().add(
                    DialogBox.getDukeDialog(response, dukeImage)
                );
            }
        } catch (DukeException e) {
            response = Ui.showLineGui() + Ui.showErrorMsgGui(e.getMessage()) + Ui.showLineGui();
            dialogContainer.getChildren().add(
                    DialogBox.getDukeDialog(response, dukeImage)
            );
        } catch (Exception e) {
            response = Ui.showLineGui() + Ui.showErrorMsgGui("     New error, please read console:")
                    +  Ui.showErrorMsgGui("     Duke will continue as per normal.") + Ui.showLineGui();
            dialogContainer.getChildren().add(
                    DialogBox.getDukeDialog(response, dukeImage)
            );
            e.printStackTrace();
        }
        userInput.clear();
    }
}