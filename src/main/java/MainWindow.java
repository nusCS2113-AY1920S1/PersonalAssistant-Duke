import controlpanel.DukeException;
import help.AutoComplete;
import guicommand.UserIcon;
import help.History;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.text.ParseException;
import java.util.TreeSet;

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
    private TextField searchBar;

    private Duke duke;
    private UserIcon userIcon;

    private static Image userImage;
    private Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/DaDuke.png"));

    private History previousFunctions = new History();

    /**
     * Initialises scroll bar and outputs Duke Welcome message on startup of GUI.
     */
    //@@author {therealnickcheong}
    @FXML
    public void initialize() throws IOException {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        scrollPane2.vvalueProperty().bind(graphContainer.heightProperty());

        dialogContainer.getChildren().addAll(
                DialogBox.getDukeDialog("enter start to begin", dukeImage));

        userIcon = new UserIcon();
        userImage = userIcon.getIcon();

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                userInput.requestFocus();
            }
        });
    }

    public void setDuke(Duke d) {
        duke = d;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to.
     * the dialog container. Clears the user input after processing.
     */
    //@@author {cctt1014}
    @FXML
    private void handleUserInput() throws IOException, ParseException, DukeException {
        String input = userInput.getText();
        graphContainer.getChildren().clear();
        if (input.equals("change icon")) {
            userIcon.changeIcon();
            userImage = userIcon.getIcon();
        }

        if (input.startsWith("graph")) {
            GraphSelector graphSelector = new GraphSelector();
            graphContainer.getChildren().addAll(
                graphSelector.getTheGraph(input, duke.getAccount())
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
        previousFunctions.addingCommandsEntered(userInput.getText());
        previousFunctions.setCurrIndex();
        userInput.clear();
    }

    //@@author therealnickcheong
    @FXML
    private void handleSearchInput() {
        String input = searchBar.getText();
        if (input.equals("")) {
            graphContainer.getChildren().clear();
        } else {
            String[] response = duke.getResponse("find " + input);
            graphContainer.getChildren().clear();
            if (!response[1].equals("")) {
                graphContainer.getChildren().clear();
                graphContainer.getChildren().addAll(
                        DialogBox.getDukeDialog(response[1], dukeImage));
            }
        }
    }

    //@@author {ChenChao19}
    @FXML
    private void autoCompleteFunction() {
        AutoComplete autoComplete = new AutoComplete();
        AutoCompletionBinding<String> suggestions = TextFields.bindAutoCompletion(userInput, sc -> {
            TreeSet<String> suggestedCommands = new TreeSet<>();
            for (String i: autoComplete.getCommandList()) {
                if (!sc.getUserText().isEmpty() && !i.equals(sc.getUserText())
                        && i.startsWith(sc.getUserText())) {
                    suggestedCommands.add(i);
                }
            }
            return suggestedCommands;
        });
        suggestions.setVisibleRowCount(3);
        suggestions.setPrefWidth(200);
    }

    @FXML
    private void help() {
        autoCompleteFunction();
        userInput.setPromptText("Commands");
        userInput.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode() == KeyCode.UP) {
                    userInput.clear();
                    if (previousFunctions.getMaxIndex() != 0) {
                        previousFunctions.setFlagTrue();
                        userInput.appendText(previousFunctions.getPreviousCommand() + "\n");
                    }
                } else if (ke.getCode() == KeyCode.DOWN) {
                    if (previousFunctions.getMaxIndex() != 0) {
                        if (previousFunctions.getCurrIndex() == previousFunctions.getMaxIndex() - 1) {
                            userInput.clear();
                            previousFunctions.setFlagForFirstPress();
                        } else {
                            userInput.clear();
                            previousFunctions.setFlagFalse();
                            userInput.appendText(previousFunctions.getNextCommand() + "\n");
                        }
                    }
                }
            }
        });
    }
}