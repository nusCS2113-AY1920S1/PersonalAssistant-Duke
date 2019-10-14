import controlpanel.Parser;
import guicommand.UserIcon;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.*;
import java.text.ParseException;

/**
 * Controller for MainWindow. Provides the layout for the other controls.
 */
public class MainWindow extends AnchorPane implements DataTransfer {

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
    private TextField searchBar;
    @FXML
    private Button sendButton;
    @FXML
    private Button searchButton;

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
    private void handleUserInput() throws IOException, ParseException {
        String input = userInput.getText();
        graphContainer.getChildren().clear();
        switch (input) {
            case "change icon":
                userIcon.changeIcon();
                userImage = userIcon.getIcon();
                break;
            case "graph monthly report":
                graphContainer.getChildren().addAll(
                        DataTransfer.getMonthlyData(duke.getAccount())
                );
                break;
            case "graph expenditure trend":
                graphContainer.getChildren().addAll(
                        DataTransfer.getExpenditureTrend(duke.getAccount())
                );
                break;
            case "graph income trend":
                graphContainer.getChildren().addAll(
                        DataTransfer.getIncomeTrend(duke.getAccount())
                );
                break;
            default:
                if (input.startsWith("graph finance status /until ")) {
                    String dateString = input.split(" /until ")[1];
                    graphContainer.getChildren().addAll(
                            DataTransfer.getCurrFinance(duke.getAccount(), Parser.shortcutTime(dateString))
                    );
                }
                break;
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

    @FXML
    private void handleSearchInput() {
        String input = searchBar.getText();
        String[] response = duke.getResponse("find " + input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getDukeDialog(response[0], dukeImage)
        );
        if(!response[1].equals("")){
            graphContainer.getChildren().clear();
            graphContainer.getChildren().addAll(
                    DialogBox.getDukeDialog(response[1], dukeImage));
        }

    }
}