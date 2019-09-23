package JavaFx;
import Interface.*;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

/**
 * Controller for MainWindow. Provides the layout for the other controls.
 */
public class MainWindow extends BorderPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private ComboBox<String> taskViewSelection;
    @FXML
    private Label currentTime;
    @FXML
    private VBox viewContainer;
    @FXML
    private VBox rightView;

    private Duke duke;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/DaDuke.png"));
    private Ui ui = new Ui();

    /**
     * This method initializes the display in the window of the GUI.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        dialogContainer.getChildren().add(DialogBoxDuke.getDukeDialog(ui.showWelcome(), dukeImage));
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("E dd/MM/yyyy hh:mm a");
        String dateString = dateFormat.format(date);
        currentTime.setText(dateString);
        taskViewSelection.getItems().addAll(
                "Today",
                "Week",
                "Upcoming",
                "Todo",
                "Event",
                "Deadline"
        );
    }

    /**
     * This method creates the Duke object.
     * @param d The object of Duke
     */
    public void setDuke(Duke d) {
        duke = d;
    }

    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = duke.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBoxUser.getUserDialog(input, userImage),
                DialogBoxDuke.getDukeDialog(response, dukeImage)
        );
        if (userInput.getText().equals("bye")) {
            PauseTransition delay = new PauseTransition(Duration.seconds(1));
            delay.setOnFinished( event -> Platform.exit() );
            delay.play();
        }
        userInput.clear();
    }

    @FXML
    private void handleTaskViewSelection() throws ParseException {
        switch (taskViewSelection.getValue()){
            case "Today":
                viewContainer.getChildren().clear();
                break;
            case "Week":
                viewContainer.getChildren().clear();
                break;
            case "Upcoming":
                viewContainer.getChildren().clear();
                break;
            case "Todo":
                viewContainer.getChildren().clear();
                break;
            case "Event":
                viewContainer.getChildren().clear();
                viewContainer.getChildren().addAll(TimetableView.getTimetableView(duke.getCurrentList()));
                break;
            case "Deadline":
                viewContainer.getChildren().clear();
                break;
        }
    }
}