package duke;

import duke.command.Command;
import duke.exception.DukeException;
import duke.parser.Parser;
import duke.storage.Storage;
import duke.tasklist.TaskList;
import duke.ui.DialogBox;
import duke.ui.ExitWindow;
import duke.ui.HelpWindow;
import duke.ui.Ui;
import javafx.animation.FadeTransition;
import javafx.application.Application;
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
        if (!Main.isScreenLoaded) {
            loadStartingScreen();
        }
        Ui ui = new Ui(this);
        duke = new Duke(ui);
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        dialogContainer.getChildren().addAll(
            DialogBox.getWelcome(duke.showWelcome())
        );
    }

    public void setDuke(Duke d) {
        duke = d;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() throws DukeException, ParseException {
        String input = userInput.getText();
        if (input.isEmpty()) {
            resultDisplay.setText("Pls input a command to proceed");
        } else {
            if (input.contains("list")) {
                resultDisplay.clear();
                listView.getItems().clear();
                dialogContainer.getChildren().addAll(
                        DialogBox.getUserDialog(input)
                );
                handleListTask();
                for (int i = 0; i < duke.getSize() / 3; i++) {
                    listView.getItems().add(duke.getList().get(i));
                }
            } else if (input.trim().substring(0, 4).equals(COMMAND_FIND)) {
                if (input.trim().equals(COMMAND_FIND)) {
                    resultDisplay.setText(ERROR_MESSAGE_GENERAL + MESSAGE_FOLLOWUP_NUll);
                } else if (input.trim().charAt(4) == ' ') {
                    String description = input.split("\\s", 2)[1].trim();
//                    System.out.println(MESSAGE_FIND);
                    for (int i = 0; i < duke.findList(description).size() / 3; i++) {
                        listViewResult.getItems().add("     " + (i + 1) + ". " + duke.findList(description).get(i));
                    }
                } else {
                    resultDisplay.setText(ERROR_MESSAGE_RANDOM);
                }
            }
        }
        userInput.clear();
    }

    public void handleListTask() {
        for (int i = 0; i < duke.getList().size() / 3; i++) {
            listView.getItems().add(duke.getList().get(i));
        }
    }

    public void handleLoadingError() {
        resultDisplay.setText(ERROR_MESSAGE_LOADING + filePath);
    }

    @FXML
    private void handleExit() {
        ExitWindow exitWindow = new ExitWindow();
        Stage stage = new Stage();
        stage.setScene(new Scene(exitWindow));
        stage.setTitle("Help");
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
            Main.isScreenLoaded = true;

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
                    fxmlLoader.<MainWindow>getController().setDuke(duke);
                } catch (IOException ex) {
                    Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

        } catch (IOException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}