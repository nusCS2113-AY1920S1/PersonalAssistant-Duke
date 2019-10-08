package JavaFx;
import Interface.*;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;


/**
 * Controller for ChatBot. Provides the layout for the other controls.
 */
public class ChatBot extends BorderPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;

    private Duke duke;

    private final Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private final Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/DaDuke.png"));
    private final Ui ui = new Ui();

    /**
     * This method initializes the display in the window of the GUI.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        dialogContainer.getChildren().add(DialogBoxDuke.getDukeDialog(ui.showWelcome(), dukeImage));
    }

    /**
     * Initialize Duke object in ChatBot controller with Duke object from MainWindow.
     * @param d Duke object from Main bridge
     */
    public void setDuke(Duke d) {
        duke = d;
    }

    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = duke.getResponse(input);

        dialogContainer.getChildren().addAll(
                DialogBoxUser.getUserDialog(input, userImage),
                DialogBoxDuke.getDukeDialog(response, dukeImage)
        );
        if (userInput.getText().equals("bye")) {
            PauseTransition delay = new PauseTransition(Duration.seconds(1));
            delay.setOnFinished( event -> Platform.exit() );
            delay.play();
        }
        userInput.clear();
    }
}