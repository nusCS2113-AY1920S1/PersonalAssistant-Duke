import help.AutoComplete;
import controlpanel.Parser;
import guicommand.UserIcon;
import help.MemorisePreviousFunctions;
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

import java.io.*;
import java.text.ParseException;
import java.util.*;

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

    private Duke duke;
    private UserIcon userIcon;

    private static Image userImage;
    private Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/DaDuke.png"));

    private MemorisePreviousFunctions previousFunctions = new MemorisePreviousFunctions();

    /**
     * Initialises scroll bar and outputs Duke Welcome message on startup of GUI.
     */
    //@@ therealnickcheong
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
    //@@ cctt1014
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
        previousFunctions.addingCommandsEntered(userInput.getText());
        previousFunctions.setCurrIndex();
        userInput.clear();
    }

    //@@ therealnickcheong
    @FXML
    private void handleSearchInput() {
        String input = searchBar.getText();
        if(input.equals("")){
            graphContainer.getChildren().clear();
        }else{
            String[] response = duke.getResponse("find " + input);
            graphContainer.getChildren().clear();
            if(!response[1].equals("")){
                graphContainer.getChildren().clear();
                graphContainer.getChildren().addAll(
                        DialogBox.getDukeDialog(response[1], dukeImage));
            }
        }

    }

    //@@ ChenChao19
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
                    if(previousFunctions.getMaxIndex() != 0) {
                        previousFunctions.setFlagTrue();
                        userInput.appendText(previousFunctions.getPreviousCommand() + "\n");
                    }
                } else if (ke.getCode() == KeyCode.DOWN) {
                    if(previousFunctions.getMaxIndex() != 0) {
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