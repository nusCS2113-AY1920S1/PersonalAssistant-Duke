package duke;

import duke.exception.DukeException;
import duke.ui.DialogBox;
import duke.ui.ExitWindow;
import duke.ui.HelpWindow;
import duke.ui.Ui;
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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import static duke.common.Messages.ERROR_MESSAGE_LOADING;
import static duke.common.Messages.filePath;

/**
 * Controller for MainWindow. Provides the layout for the other controls.
 */
public class MainWindow extends AnchorPane {

    private Duke duke;

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
    private ListView listView;

    @FXML
    public void initialize() {
        if (!Main.isScreenLoaded) {
            loadStartingScreen();
        }
        Ui ui = new Ui(this);
        duke = new Duke(ui);
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    public void setDuke(Duke d) {
        duke = d;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() throws DukeException {
        String input = userInput.getText();
//        duke.runProgram(input);
        String response = duke.getResponse(input);
        resultDisplay.setText(input);
        for (int i = 0; i < duke.getList().size(); i++) {
            listView.getItems().add(duke.getList().get(i));
        }
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getDukeDialog("hi", dukeImage)
        );
        userInput.clear();
    }

    public void handleListTask() {
        for (int i = 0; i < duke.getList().size(); i++) {
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
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
                    AnchorPane ap = fxmlLoader.load();
//                    AnchorPane parentContent = FXMLLoader.load(getClass().getResource(("/view/MainWindow.fxml")));
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