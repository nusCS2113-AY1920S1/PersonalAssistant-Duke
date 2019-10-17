package javafx;

import exception.DukeException;
import main.Duke;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;


/**
 * Controller for JavaFX.MainWindow. Provides the layout for the other controls.
 *
 * @author Lee Zhen Yu
 * @version %I%
 * @since 1.0
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

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Method that runs when duke GUI is started up.
     * It does not take any input from the terminal yet, and does not output to the terminal either.
     *
     * @param d The duke helper that is going to be initialized
     */
    //This method initializes duke
    public void setDuke(Duke d) {

        duke = d;


        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";

        String welcome = "Hello from\n"
                + logo + "\n"
                + "Hello! I'm Duke!\n";

        String greeting;

        if (duke.reminder().isEmpty()) {
            greeting = "What can I do for you?\n\n";
        } else {
            greeting = duke.reminder() + "\n\n";
        }

        welcome += greeting;

        dialogContainer.getChildren().addAll(
                DialogBox.getDukeDialog(welcome)
        );
    }


    /**
     * Creates two dialog boxes, one echoing user input and the other containing JavaFX.Main.Duke's
     * reply and then appends them to the dialog container.
     * Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() throws DukeException {
        String input = userInput.getText();
        String response = duke.run(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input),
                DialogBox.getDukeDialog(response)
        );

        //If user wants to end program, create a separate thread with a timer to shutdown
        if (input.equals("bye")) {
            // delay & exit on other thread
            new Thread(() -> {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException ex) {
                }
                System.exit(0);
            }).start();
        }
        userInput.clear();
    }
}
