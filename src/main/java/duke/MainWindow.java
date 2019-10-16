package duke;

import duke.exception.DukeException;
import duke.storage.Storage;
import duke.list.tasklist.TaskList;
import duke.ui.*;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import static duke.common.Messages.*;
import static duke.common.RecipeMessages.COMMAND_LIST_RECIPES;
import static duke.common.RecipeMessages.COMMAND_LIST_RECIPE_INGREDIENT;

/**
 * Controller for MainWindow. Provides the layout for the other controls.
 */
public class MainWindow extends AnchorPane {

    private Duke duke;
    private Storage storage;
    private TaskList taskList;
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
    public void initialize() throws ParseException, DukeException {
//        if (!Main.isScreenLoaded) {
//            loadStartingScreen();
//        }
        Ui ui = new Ui(this);
        duke = new Duke(ui);
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        dialogContainer.getChildren().addAll(
                DialogBox.getWelcome(duke.showWelcome())
        );
    }

//    public void setDuke(Duke d) {
//        duke = d;
//    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() throws DukeException, ParseException {
        String input = userInput.getText();
        if (input.isEmpty()) {
            showMessage("Pls input a command to proceed");
//            resultDisplay.setText("Pls input a command to proceed");
        } else {
            resultDisplay.clear();
//            listView.getItems().clear();
//            listViewResult.getItems().clear();
            dialogContainer.getChildren().addAll(
                    DialogBox.getUserDialog(input)
            );
            if (input.trim().equals(COMMAND_BYE)) {
                handleExit();
            } else if (input.trim().equals(COMMAND_HELP)) {
                handleHelp();
            } else {
                //will need to copy the arraylist if not duke.runProgram will be ran twice
                //causing items to be added twice
                ArrayList<String> arrayList = new ArrayList<>(duke.runProgram(input));
                showMessage(arrayList.get(0));
//                showMessage(duke.runProgram(input).get(0));
//                resultDisplay.setText(duke.runProgram(input).get(0));
                if (input.trim().contains(COMMAND_LIST_RECIPE_INGREDIENT)) {
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

    public void loadStartingScreen() {
        try {
//            Main.isScreenLoaded = true;

            StackPane pane = FXMLLoader.load(getClass().getResource(("/view/WelcomeScreen.fxml")));
            root.getChildren().setAll(pane);
            FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), pane);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.setCycleCount(1);

            FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), pane);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);
            fadeOut.setCycleCount(1);

            fadeIn.play();

            fadeIn.setOnFinished((e) -> {
                fadeOut.play();
            });

            fadeOut.setOnFinished((e) -> {
                Main.isScreenLoaded = true;
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
                    AnchorPane ap = fxmlLoader.load();
                    root.getChildren().setAll(ap);
//                    fxmlLoader.<MainWindow>getController().setDuke(duke);
                } catch (IOException ex) {
                    Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        } catch (IOException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}