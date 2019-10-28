package duke.ui;

import duke.Duke;
import duke.exception.DukeException;
import javafx.fxml.FXML;
import javafx.scene.Scene;

import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.text.ParseException;
import java.util.ArrayList;

import static duke.common.Messages.*;
import static duke.common.BookingMessages.*;
import static duke.common.RecipeMessages.COMMAND_LIST_RECIPE_INGREDIENT;

/**
 * Controller for MainWindow. Provides the layout for the other controls.
 */
public class MainWindow extends AnchorPane {

    private Duke duke;
    private Ui ui;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/DaDuke.png"));

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;

    @FXML
    private TextArea resultDisplay;

    @FXML
    private AnchorPane root;

    @FXML
    private ListView<String> listView, listViewResult;

    @FXML
    public void initialize() {

        Ui ui = new Ui(this);
        duke = new Duke(ui);
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        dialogContainer.getChildren().addAll(
                DialogBox.getWelcome(duke.showWelcome())
        );
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() throws DukeException, ParseException {
        String input = userInput.getText();
        if (input.isEmpty()) {
            showMessage("Pls input a command to proceed");
        } else {
            resultDisplay.clear();
            dialogContainer.getChildren().addAll(
                    DialogBox.getUserDialog(input)
            );
            if (input.trim().equals(COMMAND_BYE)) {
                handleExit();
            } else if (input.trim().equals(COMMAND_HELP)) {
                handleHelp();
            } else {
                ArrayList<String> arrayList = new ArrayList<>(duke.runProgram(input));
                showMessage(arrayList.get(0));
                if (input.trim().contains(COMMAND_LIST_RECIPE_INGREDIENT) || input.trim().contains(COMMAND_VIEW_ORDERS)) {
                    listViewResult.getItems().clear();
                    for (int i = 1; i < arrayList.size(); i++) {
                        listViewResult.getItems().add(arrayList.get(i));
                    }
                } else {
                    listViewResult.getItems().clear();
                    listView.getItems().clear();
                    for (int i = 1; i < arrayList.size(); i++) {
                        listView.getItems().add(arrayList.get(i));
                    }
                }
            }
        }
        userInput.clear();
    }

    public void showMessage(String message) {
        resultDisplay.setText(message);
    }

    public String getInput() {
        return userInput.getText();
    }

    public void handleLoadingError() {
        resultDisplay.setText(ERROR_MESSAGE_LOADING);
    }

    @FXML
    private void handleAddRecipe(String input) {
        RecipeWindow recipeWindow = new RecipeWindow();
        Stage stage = new Stage();
        stage.setScene(new Scene(recipeWindow));
        stage.setTitle(input);
        stage.setWidth(600);
        stage.setHeight(400);
        stage.show();
    }

    @FXML
    private void handleExit() {
        ExitWindow exitWindow = new ExitWindow();
        Stage stage = new Stage();
        stage.setScene(new Scene(exitWindow));
        stage.setTitle("Exit");
        stage.setWidth(480);
        stage.setHeight(100);
        stage.show();
    }

    @FXML
    private void handleHelp() {
        HelpWindow helpWindow = new HelpWindow();
        Stage stage = new Stage();
        stage.setScene(new Scene(helpWindow));
        stage.setTitle("Help");
        stage.setWidth(680);
        stage.setHeight(100);
        stage.show();
    }
}