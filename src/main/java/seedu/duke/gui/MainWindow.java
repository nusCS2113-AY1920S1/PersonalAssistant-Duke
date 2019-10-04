package seedu.duke.gui;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import seedu.duke.Duke;
import seedu.duke.Parser;
import seedu.duke.TaskList;
import seedu.duke.Storage;
import seedu.duke.email.EmailStorage;

import java.util.Collection;
import java.util.Iterator;
import java.util.function.UnaryOperator;
import java.util.Set;
import java.util.HashSet;

/**
 * Controller for MainWindow. Provides the layout for the other controls.
 */
public class MainWindow extends AnchorPane {
    private TaskList tasks;
    @FXML
    private VBox taskContainer;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;
    @FXML
    private ListView<String> tasksListView;
    @FXML
    private ListView<String> emailsListView;

    private Duke duke;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/DaDuke.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    public void setDuke(Duke d) {
        duke = d;
    }

    @FXML
    private void getstring() {
        userInput.setText("123");
    }

    /**
     * Constructs a text formatter.
     *
     * @return the format constructed
     */
    @FXML
    public TextFormatter<String> getTextFormatter() {
        String prefix = Parser.getInputPrefix();
        UnaryOperator<TextFormatter.Change> filter = c -> {
            if (c.getCaretPosition() < prefix.length()) {
                return null;
            } else {
                return c;
            }
        };
        TextFormatter<String> textFormatter = new TextFormatter<>(filter);
        return textFormatter;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends
     * them to the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = duke.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getDukeDialog(response, dukeImage)
        );
        setInputPrefix();
        updateTasksList();
        updateEmailsList();
        if (response.contains("Bye, hope to see you again.")) {
            Storage.saveTasks(duke.getTaskList());
            EmailStorage.saveEmails(duke.getEmailList());
            Platform.exit();
        }
    }

    /**
     * To begin the userInput textfield with a prefix either as "task" or "email". The prefix is
     * non-deletable, enter "flip" to toggle between them.
     */
    private void setInputPrefix() {
        // To apply a noFilter to userInput to remove the effect of the previous filter so that clear()
        // can work properly.
        UnaryOperator<TextFormatter.Change> noFilter = c -> {
            return c;
        };
        userInput.setTextFormatter(new TextFormatter<String>(noFilter));

        userInput.clear();
        String prefix = Parser.getInputPrefix();
        userInput.setText(prefix);

        // To apply a filter to any changes in userInput text field so that the prefix is non-deletable text.
        UnaryOperator<TextFormatter.Change> filter = c -> {
            if (c.getCaretPosition() < prefix.length()) {
                return null;
            } else {
                return c;
            }
        };
        userInput.setTextFormatter(new TextFormatter<String>(filter));
        userInput.positionCaret(prefix.length());
    }

    private void updateTasksList() {
        ObservableList<String> observableList = FXCollections.observableArrayList(
                "Task 1\nsomething", "Task 2", "Task 3", "Task 4", "Task 5", "Task 6",
                "Task 7", "Task 8", "Task 9\nAfter which: Task 9a", "Task 10", "Task 11", "Task 12", "Task 13",
                "Task 14", "Task 15", "Task 16", "Task 17\nAfter which: Task 17a", "Task 18", "Task 19", "Task 20",
                "Task 21", "Task 22", "Task 23", "Task 24", "Task 25", "Task 26", "Task 27", "Task 28");
        tasksListView.setItems(observableList);
    }

    private void updateEmailsList() {
        ObservableList<String> observableList = FXCollections.observableArrayList(
                "Email 1\nsomething", "Email 2", "Email 3", "Email 4", "Email 5", "Email 6",
                "Email 7", "Email 8", "Email 9\nAfter which: Email 9a", "Email 10", "Email 11", "Email 12", "Email 13",
                "Email 14", "Email 15", "Email 16", "Email 17\nAfter which: Email 17a", "Email 18", "Email 19", "Email 20",
                "Email 21", "Email 22", "Email 23", "Email 24", "Email 25", "Email 26", "Email 27", "Email 28");
        emailsListView.setItems(observableList);
    }
}