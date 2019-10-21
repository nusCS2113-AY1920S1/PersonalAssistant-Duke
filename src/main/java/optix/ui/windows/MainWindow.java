package optix.ui.windows;

import com.jfoenix.controls.JFXTextField;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import optix.Optix;

import java.io.IOException;

public class MainWindow extends AnchorPane {
    //// All the elements on the left side of the window.
    @FXML
    private VBox display;

    //// All the elements on the right side of the window.
    @FXML
    private VBox chatBox;
    @FXML
    private JFXTextField userInput;
    @FXML
    private ImageView icon;

    private Image optixImage = new Image(this.getClass().getResourceAsStream("/img/optixImage.png"));
    private Image userImage = new Image(this.getClass().getResourceAsStream("/img/userImage.png"));
    private Image optixIcon = new Image(this.getClass().getResourceAsStream("/img/optixIcon.png"));

    private Optix optix;

    public MainWindow(Optix optix) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/MainWindow.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.optix = optix;
        String greetings = this.optix.getResponse();
        chatBox.getChildren().add(DialogBox.getOptixDialog(greetings, optixImage));

        icon.setImage(optixIcon);
    }

    @FXML
    private void handleResponse() {
        String fullCommand = userInput.getText();
        String taskType = optix.runGUI(fullCommand);
        String response = optix.getResponse();

        chatBox.getChildren().addAll(
                DialogBox.getUserDialog(fullCommand, userImage),
                DialogBox.getOptixDialog(response, optixImage)
        );

        switch (taskType) {
        case "bye":
            shutDown();
            break;
        case "show":
            displayShows();
            break;
        case "seat":
            displaySeats();
            break;
        default:
            break;
        }
        userInput.clear();
    }

    private void shutDown() {
        PauseTransition delay = new PauseTransition(Duration.seconds(1));
        delay.setOnFinished(event -> Platform.exit());
        delay.play();
    }

    private void displayShows() {
        clearDisplay();
    }

    private void displaySeats() {
        clearDisplay();
    }

    private void clearDisplay() {
        display.getChildren().removeAll(display.getChildren());
    }
}
