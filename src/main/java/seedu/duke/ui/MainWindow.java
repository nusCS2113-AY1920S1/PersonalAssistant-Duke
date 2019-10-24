package seedu.duke.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Popup;
import javafx.stage.Screen;
import javafx.stage.Stage;
import seedu.duke.CommandParseHelper;
import seedu.duke.Duke;

import java.util.ArrayList;

/**
 * Controller for MainWindow. Provides the layout for the other controls.
 */
public class MainWindow extends AnchorPane {
    private static Stage mainStage;
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
    private UserInputHandler userInputHandler;
    private boolean isShowingEmail = false;
    private boolean isUpKey;
    private int inputListIndex;
    private ArrayList<String> inputList = new ArrayList<>();
    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/winner.png"));
    private Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/robot.png"));

    /**
     * Starts up GUI screen by loading welcome message, task list and email list.
     */
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
        //updateTasksList();
        //updateEmailsList();

        userInputHandler = new UserInputHandler(userInput, sendButton);
        setInputPrefix();

        // disable webView so that userInput can get focus
        webView.setDisable(true);
        userInput.requestFocus();
    }

    /**
     * Resizes AnchorPane to fit window dimensions.
     */
    private void resizeToFitScreen() {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double screenHeight = screenBounds.getHeight(); //680
        double screenWidth = screenBounds.getWidth(); //1280
        rootAnchorPane.setPrefHeight(screenHeight - 30);
        rootAnchorPane.setPrefWidth(screenWidth);
    }

    /**
     * Display a message on the main window chat box.
     *
     * @param msg     main message to be displayed
     * @param input   the user input triggered this display
     * @param command the command executed to produce this message
     */
    public void showGuiMessage(String msg, String input, String command) {
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getDukeDialog(command + "\n\n" + msg, dukeImage)
        );
    }

    public void setKeyBinding(Scene scene) {
        new KeyBinding(scene, userInput, this);
    }

    public void setMainStage(Stage stage) {
        mainStage = stage;
    }

    /**
     * Handle userInput key event.
     *
     * @param keyCode key code of the key pressed when focus is in the userInput.
     */
    public void handleUserInputKeyEvent(KeyCode keyCode) {
        switch (keyCode) {
        case ENTER:
            sendButton.fire();
            break;
        case UP:
            isUpKey = true;
            getPrevInput();
            break;
        case DOWN:
            isUpKey = false;
            getPrevInput();
            break;
        case LEFT:
            userInputHandler.moveCaretLeft();
            break;
        case RIGHT:
            userInputHandler.moveCaretRight();
            break;
        case BACK_SPACE:
            userInputHandler.setTextBackSpace();
            break;
        case DELETE:
            userInputHandler.setTextDelete();
            break;
        default:
            return;
        }
    }

    /**
     * Handle scene key event.
     *
     * @param keyCode key code of the key pressed when focus is in any nodes in the scene.
     */
    public void handleSceneKeyEvent(KeyCode keyCode) {
        if (keyCode == KeyCode.ESCAPE) {
            toggleEmailDisplay();
        }
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends
     * them to the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        webView.setDisable(false);
        String input = userInput.getText();
        Duke.getUI().respond(input);
        Duke.getUI().syncWithModel();
        //updateTasksList();
        //updateEmailsList();
        setInputPrefix();
        if (input.contains("clear")) {
            dialogContainer.getChildren().clear();
        }
        if (input.contains("email show")) {
            updateHtml();
        }
        if (input.contains("bye")) {
            exit();
        }
        updateInputList(input);
    }

    private void updateHtml() {
        webEngine.loadContent(Duke.getUI().getEmailContent());
        showHtml();
    }

    private void exit() {
        Duke.getUI().exit();
    }

    /**
     * Gets the input without prefixes.
     */
    private void updateInputList(String input) {
        String stripedInput = input.split(" ", 2)[1];
        if (inputList.contains(stripedInput)) {
            inputListIndex = inputList.indexOf(stripedInput);
        } else {
            inputList.add(stripedInput);
            inputListIndex = inputList.size();
        }
    }

    /**
     * Shows the previous inputs with the prefix. The prefix is non-deletable while the previous input shown
     * can be edited.
     */
    private void getPrevInput() {
        String prefix = CommandParseHelper.getInputPrefix();
        String prevInput = navigateInputList();
        userInputHandler.setUserInputText(prefix + prevInput);
    }

    /**
     * Navigates the inputList and gets the previous input depending on which arrow key is pressed.
     *
     * @return prevInput to be shown in the textfield
     */
    private String navigateInputList() {
        String prevInput = "";
        if (isUpKey) {
            if (inputListIndex < 1) {
                inputListIndex = inputList.size();
            }
            inputListIndex--;
            prevInput = inputList.get(inputListIndex);
        } else {
            if (inputListIndex > inputList.size() - 2) {
                inputListIndex = -1;
            }
            inputListIndex++;
            prevInput = inputList.get(inputListIndex);
        }
        return prevInput;
    }

    private void toggleEmailDisplay() {
        if (isShowingEmail) {
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
        String prefix = Duke.getUI().getPrefix();
        userInputHandler.setUserInputText(prefix);
    }

    /**
     * Updates the gui for task list display.
     *
     * @param taskStringList the task list to be displayed.
     */
    public void updateTasksList(ArrayList<String> taskStringList) {
        ObservableList<String> observableList = FXCollections.observableArrayList();
        for (int i = 0; i < taskStringList.size(); i++) {
            String taskString = taskStringList.get(i);
            String output = (i + 1) + ". " + taskString;
            observableList.add(output);
        }
        tasksListView.setItems(observableList);
    }

    /**
     * Updates the gui for email list display.
     *
     * @param emailStringList the email list to be displayed.
     */
    public void updateEmailsList(ArrayList<String> emailStringList) {
        ArrayList<EmailHBoxCell> list = new ArrayList<>();
        for (int i = 0; i < emailStringList.size(); i++) {
            list.add(new EmailHBoxCell(emailStringList.get(i), i));
        }
        ObservableList<EmailHBoxCell> observableList = FXCollections.observableList(list);
        emailsListView.setItems(observableList);
    }

    /**
     * Shows a popup displaying long text message.
     *
     * @param text the text that is to be displayed in the popup
     */
    public void showTextPopup(String text) {
        final Popup popup = new Popup();
        AnchorPane outerPane = prepareAnchorPanForPopup();
        ScrollPane scroll = prepareScrollPaneForPopup();
        Button button = prepareButtonForPopup(popup);
        combinePopupComponents(text, outerPane, scroll, button);
        popup.getContent().add(outerPane);
        popup.show(mainStage);
    }

    private void combinePopupComponents(String text, AnchorPane outerPane, ScrollPane scroll, Button button) {
        AnchorPane pane = new AnchorPane();
        Label label = new Label(text);
        pane.getChildren().add(label);
        scroll.setContent(pane);
        outerPane.getChildren().addAll(scroll, button);
    }

    private Button prepareButtonForPopup(Popup popup) {
        Button button = new Button("Close");
        button.setPrefSize(80, 16);
        button.setLayoutX(380);
        button.setLayoutY(610);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                popup.hide();
                System.out.println(popup);
            }
        });
        return button;
    }

    private ScrollPane prepareScrollPaneForPopup() {
        ScrollPane scroll = new ScrollPane();
        scroll.setPrefSize(796, 600);
        scroll.setLayoutX(2);
        scroll.setPadding(new Insets(0, 10, 0, 10));
        return scroll;
    }

    private AnchorPane prepareAnchorPanForPopup() {
        AnchorPane outerPane = new AnchorPane();
        outerPane.setPrefSize(800, 650);
        outerPane.setStyle("-fx-background-color: #FFFFFF;"
                + "-fx-border-color: black;");
        return outerPane;
    }

    public static class EmailHBoxCell extends HBox {
        EmailHBoxCell(String email, int i) {
            super();

            Label emailName = new Label();
            emailName.setWrapText(true);
            emailName.setText((i + 1) + ". " + email);
            emailName.setMaxWidth(USE_COMPUTED_SIZE);
            HBox.setHgrow(emailName, Priority.ALWAYS);

            this.getChildren().addAll(emailName);
        }
    }
}