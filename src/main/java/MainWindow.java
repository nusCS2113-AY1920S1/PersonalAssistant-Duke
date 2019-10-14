import guicommand.UserIcon;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * Controller for MainWindow. Provides the layout for the other controls.
 */
public class MainWindow extends AnchorPane {

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    public ScrollPane scrollPane2;
    @FXML
    public VBox graphContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Duke duke;
    private UserIcon userIcon;

    private static Image userImage;
    private Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/DaDuke.png"));

    /**
     * Initialises scroll bar and outputs Duke Welcome message on startup of GUI.
     */
    @FXML
    public void initialize() throws IOException {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        scrollPane2.vvalueProperty().bind(graphContainer.heightProperty());

        dialogContainer.getChildren().addAll(
                DialogBox.getDukeDialog("enter start to begin", dukeImage));

        userIcon = new UserIcon();
        userImage = userIcon.getIcon();
    }

    public void setDuke(Duke d) {
        duke = d;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to.
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() throws IOException {
        String input = userInput.getText();

        if (input.equals("change icon")) {
            userIcon.changeIcon();
            userImage = userIcon.getIcon();
        } else if (input.equals("graph monthly report")) {
            graphContainer.getChildren().clear();
            float[] yData = duke.getMonthlyData();
            String[] xData = new String[]{"Income", "Expenditure"};
            graphContainer.getChildren().addAll(
                    Histogram.getHistogram("The Month Report", xData, yData)
            );
        }

        String[] response = duke.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getDukeDialog(response[0], dukeImage)
        );
        if (!response[1].equals("")) {
            graphContainer.getChildren().clear();
            graphContainer.getChildren().addAll(
                    DialogBox.getDukeDialog(response[1], dukeImage));
        }
        userInput.clear();
    }
}