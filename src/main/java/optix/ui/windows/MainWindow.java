package optix.ui.windows;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import optix.Optix;
import optix.commons.model.ShowMap;
import optix.commons.model.Theatre;
import optix.util.OptixDateFormatter;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;

public class MainWindow extends AnchorPane {
    @FXML
    private JFXButton showButton;
    @FXML
    private JFXButton financeButton;
    @FXML
    private JFXButton archiveButton;
    @FXML
    private JFXButton helpButton;

    //// All the elements on the left side of the window.
    @FXML
    private VBox display;
    @FXML
    private Label tabName;

    //// All the elements on the right side of the window.
    @FXML
    private ScrollPane scrollPane;
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

    /**
     * Initialize the main display for Optix.
     * @param optix The software Optix.
     */
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
        displayShows();
    }

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(chatBox.heightProperty());
    }

    @FXML
    private void handleResponse() {
        String fullCommand = userInput.getText();
        String taskType = optix.runGui(fullCommand);
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
            tabName.setText("Show");
            displayShows();
            break;
        case "seat":
            tabName.setText("Seat");
            displaySeats(fullCommand);
            break;
        case "archive":
            tabName.setText("Archive");
            displayFinance();
            break;
        case "finance":
            tabName.setText("Finance");
            displayFinance();
            break;
        case "help":
            displayHelp();
            break;
        default:
            break;
        }
        userInput.clear();
    }

    private void shutDown() {
        PauseTransition delay = new PauseTransition(new Duration(500));
        delay.setOnFinished(event -> Platform.exit());
        delay.play();
    }

    private void displayShows() {
        clearDisplay();
        for (Map.Entry<LocalDate, Theatre> entry : optix.getShowsGui().entrySet()) {
            display.getChildren().add(ShowController.displayShow(entry.getValue(), entry.getKey()));
        }
    }

    private void displayFinance() {
        clearDisplay();
        for (Map.Entry<LocalDate, Theatre> entry : optix.getShowsGui().entrySet()) {
            display.getChildren().add(FinanceController.displayFinance(entry.getValue(), entry.getKey()));
        }
    }

    private void displaySeats(String fullCommand) {
        String[] splitStr = fullCommand.split("\\|");
        if (!optix.getShowsGui().isEmpty()) {
            clearDisplay();

            LocalDate localDate = new OptixDateFormatter().toLocalDate(splitStr[1].trim());
            ShowMap shows = optix.getShows();
            Theatre theatre = shows.get(localDate);

            display.getChildren().add(SeatsDisplayController.displaySeats(theatre, localDate));
        }
    }

    @FXML
    private void displayHelp() {
        clearDisplay();
        tabName.setText("Help");
        display.getChildren().add(HelpWindow.getHelpWindow());
    }

    @FXML
    private void clickShow() {
        optix.resetShows();
        tabName.setText("Show");
        displayShows();
    }

    @FXML
    private void clickArchive() {
        optix.resetArchive();
        tabName.setText("Archive");
        displayFinance();
    }

    @FXML
    private void clickFinance() {
        optix.resetShows();
        tabName.setText("Finance");
        displayFinance();
    }

    private void clearDisplay() {
        display.getChildren().removeAll(display.getChildren());
    }
}
