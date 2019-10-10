package seedu.duke.gui;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Screen;
import javafx.util.Duration;
import seedu.duke.Duke;
import java.util.*;
import seedu.duke.CommandParser;
import seedu.duke.UI;
import seedu.duke.task.TaskList;
import seedu.duke.task.TaskStorage;
import seedu.duke.email.EmailStorage;
import seedu.duke.task.entity.Deadline;
import seedu.duke.task.entity.Event;
import seedu.duke.task.entity.Task;

import java.util.function.UnaryOperator;

/**
 * Controller for MainWindow. Provides the layout for the other controls.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private AnchorPane rootAnchorPane;
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
    private ListView<EmailHBoxCell> emailsListView;
    @FXML
    private WebView webView;

    private WebEngine webEngine;
    private TaskList tasks;

    private ArrayList<String> inputList = new ArrayList<>();

    private Duke duke;
    private UI ui;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/DaDuke.png"));

    @FXML
    public void initialize() {
        resizeToFitScreen();

        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());

        String welcome = "Hi, I'm Duke!\nHow may I help you?";

        // show welcome message
        dialogContainer.getChildren().addAll(
                DialogBox.getDukeDialog(welcome, dukeImage)
        );

        // show email
        webEngine = webView.getEngine();
        webEngine.load("https://www.google.com");

        // initialize GUI with database
        updateTasksList();
        updateEmailsList();
        setInputPrefix();

        // disable webView so that userInput can get focus
        webView.setDisable(true);
        userInput.requestFocus();
    }

    private void resizeToFitScreen() {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double screenHeight = screenBounds.getHeight(); //680
        double screenWidth = screenBounds.getWidth(); //1280
        rootAnchorPane.setPrefHeight(screenHeight-30);
        rootAnchorPane.setPrefWidth(screenWidth);
    }

    public void setDuke(Duke d) {
        duke = d;
        ui = duke.getUI();
        ui.setupGui(dialogContainer, userImage, dukeImage);
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends
     * them to the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {

        webView.setDisable(false);
        String input = userInput.getText();
        duke.respond(input);

        //String response = ui.getResponseMsg();
        //dialogContainer.getChildren().addAll(
        //        DialogBox.getUserDialog(input, userImage),
        //        DialogBox.getDukeDialog(command + "\n\n" + response, dukeImage)
        //);

        setInputPrefix();
        updateTasksList();
        updateEmailsList();
        if (input.contains("clear")) {
            dialogContainer.getChildren().clear();
        }
        if (input.contains("email show")) {
            updateHtml();
        }
        if (input.contains("bye")) {
            exit();
        }
        getInput(input);
    }

    private void updateHtml() {
        String emailPath = ui.getEmailPath();
        webEngine.load(emailPath);
        showHtml();
    }

    private void exit() {
        TaskStorage.saveTasks(duke.getTaskList());
        EmailStorage.saveEmails(duke.getEmailList());
        PauseTransition delay = new PauseTransition(Duration.seconds(1));
        delay.setOnFinished( event -> Platform.exit() );
        delay.play();
    }

    int i;
    /**
     * Gets the input without prefixes
     */
    private void getInput(String input) {
        input = input.split(" ", 2)[1];
        if (inputList.contains(input)) {
            i = inputList.indexOf(input);
        }
        else {
            inputList.add(input);
            i = inputList.size();
        }
    }

    boolean isShowingEmail = false;
    boolean isUpKey;
    @FXML
    private void handleKeyEvent(KeyEvent e) {
        String type = e.getEventType().getName();
        KeyCode keyCode = e.getCode();
        String keyInfo = type + ": Key Code=" + keyCode.getName() + ", Text=" + e.getText() + "\n";
        // print key pressed info to terminal for debugging purpose.
        //System.out.println(keyInfo);

        // Toggle email or html display if ESC key is pressed
        if (e.getCode() == KeyCode.ESCAPE) {
            toggleEmailDisplay();
            e.consume();
        }
        // Gets previous input if Up Arrow key is pressed
        else if (e.getCode() == KeyCode.UP) {
            isUpKey = true;
            getPrevInput();
            e.consume();
        }
        // Gets previous input if Down Arrow key is pressed
        else if (e.getCode() == KeyCode.DOWN) {
            isUpKey = false;
            getPrevInput();
            e.consume();
        }
    }

    /**
     * Shows the previous inputs with the prefix. The prefix is non-deletable while the previous
     * input shown can be edited
     */
    private void getPrevInput() {
        UnaryOperator<TextFormatter.Change> noFilter = c -> {
            return c;
        };
        userInput.setTextFormatter(new TextFormatter<String>(noFilter));

        userInput.clear();

        String prefix = CommandParser.getInputPrefix();
        String prevInput = navigateInputList();

        userInput.setText(prefix + prevInput);

        UnaryOperator<TextFormatter.Change> filter = c -> {
            if (c.getCaretPosition() < prefix.length()) {
                return null;
            } else {
                return c;
            }
        };
        userInput.setTextFormatter(new TextFormatter<String>(filter));
        userInput.positionCaret(prefix.length() + prevInput.length());
    }

    /**
     * Navigates the inputList and gets the previous input depending on which arrow key is pressed
     * @return prevInput to be shown in the textfield
     */
    private String navigateInputList() {
        String prevInput = "";
        if (isUpKey == true) {
            if (i < 1) {
                i = inputList.size();
            }
            i--;
            prevInput = inputList.get(i);
        }
        else {
            if (i > inputList.size() - 2) {
                i = -1;
            }
            i++;
            prevInput = inputList.get(i);
        }
        return prevInput;
    }

    private void toggleEmailDisplay() {
        if(isShowingEmail) {
            showEmailList();
        } else {
            showHtml();
        }
    }

    private void showHtml() {
        emailsListView.setMaxHeight(0);
        webView.setMaxHeight(800);
        isShowingEmail = true;
    }

    private void showEmailList() {
        webView.setMaxHeight(0);
        emailsListView.setMaxHeight(800);
        isShowingEmail = false;
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
        String prefix = CommandParser.getInputPrefix();
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
        ObservableList<String> observableList = FXCollections.observableArrayList();
        for (int i = 0; i < Duke.getTaskList().size(); i++) {
            Task task = Duke.getTaskList().get(i);
            //String output = (i + 1) + ". " + task.getName() + "\n" +
            //        (task.getDone() ? "\u2713" : "\u2718") + "  " + task.getTaskType();
            //switch (Duke.getTaskList().get(i).getTaskType()) {
            //case Deadline:
            //    Deadline deadline = (Deadline) Duke.getTaskList().get(i);
            //    output += "\nBy: " + deadline.getTime();
            //    break;
            //case Event:
            //    Event event = (Event) Duke.getTaskList().get(i);
            //    output += "\nAt: " + event.getTime();
            //    break;
            //}
            //if (task.getDoAfterDescription() != null && task.getDoAfterDescription() != "") {
            //    output += "\nAfter which: " + task.getDoAfterDescription();
            //}
            String output = task.toString();
            observableList.add(output);
        }
        tasksListView.setItems(observableList);
    }

    private void updateEmailsList() {
//        ObservableList<String> observableList = FXCollections.observableArrayList();
//        for (int i = 0; i < Duke.getEmailList().size(); i++) {
//            observableList.add((i+1) + ". " + Duke.getEmailList().get(i).toFileString());
//        }
        ArrayList<EmailHBoxCell> list = new ArrayList<>();
        for (int i = 0; i < Duke.getEmailList().size(); i++) {
            list.add(new EmailHBoxCell(Duke.getEmailList().get(i).toFileString(), i));
        }
        ObservableList<EmailHBoxCell> observableList = FXCollections.observableList(list);
        emailsListView.setItems(observableList);
    }

    public static class EmailHBoxCell extends HBox {

        EmailHBoxCell(String email, int i) {
            super();

            Label emailName = new Label();
            emailName.setWrapText(true);
            emailName.setText((i+1) + ". " + email);
            emailName.setMaxWidth(USE_COMPUTED_SIZE);
            HBox.setHgrow(emailName, Priority.ALWAYS);

            this.getChildren().addAll(emailName);
        }
    }
}