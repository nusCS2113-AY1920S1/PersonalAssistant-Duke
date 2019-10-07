package duke.ui;

import duke.Duke;
import duke.Main;
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

    private Duke duke;
    private static final String FXML = "MainWindow.fxml";
    private Stage primaryStage;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/user.png"));

    /**
     * Initialises the MainWindow.
     */
    public MainWindow(Stage primaryStage) {
        super(FXML, primaryStage);
        this.primaryStage = primaryStage;
        primaryStage.getScene().getStylesheets().addAll(
                this.getClass().getResource("/css/mainStyle.css").toExternalForm());
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

        duke.getResponse(input);
    }
}
